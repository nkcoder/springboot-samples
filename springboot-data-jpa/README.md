# SpringBoot Data JPA

JPA只是一个规范，Spring Boot 默认使用 Hibernate 实现，如果你选择其它实现，比如`eclipselink`，则需要在依赖中排除 Hibernate，并添加 `eclipselink`：

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion") {
            exclude group: "org.hibernate", module: "hibernate-entitymanager"
        }
        implementation group: 'org.eclipse.persistence', name: 'eclipselink', version: '2.7.4'
    }

## 创建 Entity 类

首先需要创建实体类，JPA 的实体类需要使用`@Entity`注解，并且需要有一个用`@Id`注解的 id 字段，作为实体对应的数据库表中的记录的唯一标识符。
`@Table`指定 entity 对应的数据库表的表名，不是必须的，表名默认为 entity 的名字（Spring Boot 会处理名称的映射）。
JPA 需要一个无参的构造函数，因此使用 lombok 的`@NoArgsConstructor`。

```java
@Getter
@Entity
@Table(name = "player")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private final String name;

  private final String team;

  private final LocalDateTime bornAt;

}
```

## 使用 JPA Repository

Spring Data 为`JPA`、`Redis`、`MongoDB`等提供了统一的数据访问层，即`Repository`接口。我们只需要定义一个接口`Repository`或其某个子类，如`CrudRepository`、`JpaRepository`等，Spring Data 会在运行时自动生成该接口的实现。

`CrudRepository`增加了一些基本的 CURD 的方法，`JpaRepository`因为继承了`PagingAndSortingRepository`，额外提供了分页和排序的方法。

`Repository`的第一个参数表示 Entity 的类型，第二个参数表示 Entity 的 ID 的类型。

`@Repository`注解用于 Spring 自动扫描并注册为 Bean，同时将 JPA 的异常转换为`DataAccessException`，因为异常转换是通过 proxy 实现的，所以 Repository 类不能声明为 final 的。

```java
@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {}
```

### Repository 方法签名解析规则

如果 Repository 及其子类提供的操作无法满足要求，可以根据命名规范增加新的方法。Spring Data 会分析方法签名，生成要执行的查询语句，具体的命名规范为：

    动词 + 可选的实体类型 + by + 断言 + 可选的排序

比如：

```java
Player readByIdAndBornAtBetween(Long id, LocalDateTime from, LocalDateTime to);
```

第一个是动词，

- 如果是查询语句，可以是`get`, `read`, `find`
- 保存就是`save`，删除`delete`，还有`exists`等

第二个是实体类型，是可选的，因为`Repository`的第一个参数已经表明了实体的类型。

然后是`by`，后面的断言是实体的字段与条件的组合，组合之间可以使用`and`或`or`连接。Spring Data JPA 支持以下条件：

    IsAfter, After, IsGreaterThan, GreaterThan
    IsGreaterThanEqual, GreaterThanEqual
    IsBefore, Before, IsLessThan, LessThan
    IsLessThanEqual, LessThanEqual
    IsBetween, Between
    IsNull, Null
    IsNotNull, NotNull
    IsIn, In
    IsNotIn, NotIn
    IsStartingWith, StartingWith, StartsWith
    IsEndingWith, EndingWith, EndsWith IsContaining, Containing, Contains IsLike, Like
    IsNotLike, NotLike
    IsTrue, True
    IsFalse, False
    Is, Equals
    IsNot, Not
    IgnoringCase, IgnoresCase

### 使用@Query 查询

如果通过 Spring Data JPA 解析规则创建的方法不能满足要求或者太麻烦，还可以使用`@Query`通过 SQL 语句自定义查询操作：

`@Query`的`nativeQuery`表示这是一个 native 的 SQL 语句。
`@Param()`可以用于指定命名参数，默认是位置参数。

如果是更新或删除等修改操作，还需要加上`@Modifying`，如：

```java
  @Query(value = "SELECT p FROM Player p where p.name = :name")
  Player findByNameParam(@Param("name") String name);

  @Query(value = "SELECT p FROM Player p where p.name = ?1")
  Player findByName(String name);

  @Query(value = "SELECT id, name, team, born_at FROM player where name = ?1", nativeQuery = true)
  Player findByNameNativeQuery(String name);

  @Query(value = "UPDATE player SET team = ?2 WHERE name = ?1", nativeQuery = true)
  @Modifying
  void updateTeamByName(String name, String team);
```

### 分页与排序

在所有查询都可以额外接受一个参数，`Pageable`或`Sort`，用于分页和排序。但是它们不能同时出现，因为`Pageable`已经包含了`Sort`。

返回值可以是`List<T>`，`Iterable<T>`，以及`Page<T>`，根据实际需要选择其中`Page`的`getContent()`表示该页的内容，`getTotalElements()`表示总的数量。

```java
List<Player> findByTeam(String team, Pageable pageable);

List<Player> findByTeam(String team, Sort sort);

Page<Player> findByTeam(String team, Pageable pageable);

@Query(value = "SELECT p FROM Player p where p.team = ?1")
List<Player> findByTeamAndPage(String name, Pageable pageable);

Page<T> findAll(Pageable pageable);
```

### 使用 Specification 构造可扩展的查询

Spring Data Jpa 提供`Specification`用于构造灵活的、可扩展的查询。比如，用户可能使用不同的搜索条件组合成不同的查询，我们不能根据每一种可能出现的查询条件都去构造一个 query，`Specification`就是根据实际的条件构造查询，可以组合，因此更灵活，也更容易复用。

使用`Specification`，需要继承`JpaSpecificationExecutor`，它提供了根据 Specification 查询的方法：

```java
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>,
    JpaSpecificationExecutor<Player> {}
```

```java
List<T> findAll(@Nullable Specification<T> spec);

Page<T> findAll(@Nullable Specification<T> spec, Pageable pageable);

List<T> findAll(@Nullable Specification<T> spec, Sort sort);
```

首先我们定义`Specification`，可以直接构造，也可以继承来构造子类：

```java
public class PlaySpecification {

  public static Specification<Player> bornAtYearsAgo(int years) {
    return (Specification<Player>) (root, query, criteriaBuilder) -> {
      LocalDateTime yearsAgo = LocalDateTime.now().minusYears(years);
      return criteriaBuilder.greaterThan(root.get("bornAt"), yearsAgo);
    };
  }

  public static Specification<Player> teamEquals(String team) {
    return (Specification<Player>) (root, query, builder) ->
        builder.equal(root.get("team"), team);
  }

}
```

`Specification`类提供了`where`, `and`, `or`, `not`等条件组合：

```java
playerRepository.findAll(bornAtYearsAgo(1));
playerRepository.findAll(teamEquals("LA"));
playerRepository.findAll(where(bornAtYearsAgo(1)).and(teamEquals("LA")));
```

## 参考

- [Spring Data JPA - Reference Documentation](https://docs.spring.io/spring-data/jpa/docs/2.1.9.RELEASE/reference/html/#specifications)
- [REST Query Language with Spring Data JPA Specifications](https://www.baeldung.com/rest-api-search-language-spring-data-specifications)
