# SpringBoot Data JPA

JPA只是一个规范，Spring Boot 默认使用 Hibernate 实现，如果你选择其它实现，比如`eclipselink`，则需要在依赖中排除 Hibernate，并添加 `eclipselink`：

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion") {
            exclude group: "org.hibernate", module: "hibernate-entitymanager"
        }
        implementation group: 'org.eclipse.persistence', name: 'eclipselink', version: '2.7.4'
    }

## 创建 Entity 类

实体类与数据库表是一一对应的，实体类有以下特点：
- 需要使用`@Entity`注解，表示这是一个实体
- 需要有一个用`@Id`注解的字段，作为数据库表中的记录的唯一标识符
- 需要使用`@Table`注解，表示这个实体的数据会持久化到数据库的表中，表名默认为实体名，可以通过name参数指定
- JPA要求实体需要一个无参构造函数，可以直接使用lombok的`@Data`注解，但是由于对所有字段都会生成`setter`方法，破坏了面向对象的封装性，所以这里采用`@Getter`, `@NoArgsConstructor`以及`@RequiredArgsConstructor`组合。

```java
@Getter
@Entity
@Table(name = "player")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private final String name;

  private final Integer teamId;

  private final LocalDate joinAt;

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

默认继承来的方法基本可以满足基于主键的增删改查以及分页排序等需求了。

### Repository 方法签名解析规则

如果 Repository 及其子类提供的操作无法满足要求，可以根据命名规范增加新的方法。Spring Data 会分析方法签名，生成要执行的查询语句，具体的命名规范为：

    动词 + (可选的)实体类型 + by + 断言 + (可选的)排序

比如：

```java
Player readByIdAndBornAtBetween(Long id, LocalDateTime from, LocalDateTime to);
```

第一个是动词，

- 如果是查询语句，可以是`get`, `read`, `find`，一般默认是`find`
- 保存就是`save`，删除`delete`，还有`exists`, `count`等

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

- `@Query`的`nativeQuery`表示这是一个标准的、通用的SQL 语句。
- `@Param()`可以用于指定命名参数，默认是位置参数。

如果是更新或删除等修改操作，还需要加上`@Modifying`，并且其返回值只能是`void`或`integer`：

```java
@Query(value = "SELECT p FROM Player p where p.name = :name")
Player findByNameNamedParam(@Param("name") String name);

@Query(value = "SELECT p FROM Player p where p.name = ?1")
Player findByNamePositionParam(String name);

@Query("SELECT p FROM Player p")
Page<Player> findAllAndPage(Pageable pageable);

@Query(value = "SELECT id, name, team_id, join_at FROM player where name = ?1", nativeQuery = true)
Player findByNameNativeQuery(String name);

@Query(value = "UPDATE player SET name = :name WHERE id = :id", nativeQuery = true)
@Modifying
int updateNameById(@Param("id") Integer id, @Param("name") String name);
```

### 分页与排序

在所有查询都可以额外接受一个参数，`Pageable`或`Sort`，用于分页和排序。但是它们不能同时出现，因为`Pageable`已经包含了`Sort`。

返回值可以是`List<T>`，`Iterable<T>`，以及`Page<T>`，根据实际需要选择其中`Page`的`getContent()`表示该页的内容，`getTotalElements()`表示总的数量。

```java
Page<Player> findByJoinAtAfter(LocalDate from, Pageable pageable);

List<Player> findByTeamId(Integer teamId, Sort sort);
```

### 使用Specification构造查询

对应一些复杂的API，构造查询/过滤时，使用简单的字段组合很难满足要求。DSL提供了一种更灵活的方式构造复杂多变的查询，并且易于复用。

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
public class PlayerSpecification implements Specification<Player> {

  private final SearchCriteria searchCriteria;

  public PlayerSpecification(SearchCriteria searchCriteria) {
    this.searchCriteria = searchCriteria;
  }

  @Override
  public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query,
      CriteriaBuilder criteriaBuilder) {
    String key = searchCriteria.getKey();
    Operation operation = searchCriteria.getOperation();
    Object value = searchCriteria.getValue();

    Predicate predicate = null;
    Class<?> keyType = root.get(key).getJavaType();

    switch (operation) {
      case GREATER_THAN:
        if (keyType == LocalDate.class) {
          predicate = criteriaBuilder.greaterThan(root.get(key), (LocalDate) value);
        } else {
          predicate = criteriaBuilder.greaterThan(root.get(key), value.toString());
        }
        break;
      case LESS_THAN:
        if (keyType == LocalDate.class) {
          predicate = criteriaBuilder.lessThan(root.get(key), (LocalDate) value);
        } else {
          predicate = criteriaBuilder.lessThan(root.get(key), value.toString());
        }
        break;
      case EQUAL:
        if (root.get(key).getJavaType() == String.class) {
          predicate = criteriaBuilder.like(root.get(key), "%" + value + "%");
        } else {
          predicate = criteriaBuilder.equal(root.get(key), value);
        }
    }
    return predicate;
  }
}
```

```java
@Getter
@RequiredArgsConstructor
public class SearchCriteria {

  private final String key;
  private final Operation operation;
  private final Object value;
}
```

`Specification`类提供了`where`, `and`, `or`, `not`等条件组合：

```java
playerRepository.findAll(bornAtYearsAgo(1));
playerRepository.findAll(teamEquals("LA"));
playerRepository.findAll(where(bornAtYearsAgo(1)).and(teamEquals("LA")));
```

项目的源码见：[SpringBoot Examples#SpringBoot-Data-JPA](https://github.com/nkcoder/springboot-examples/tree/master/springboot-data-jpa)，文中示例的使用部分见对应的测试用例。

## 参考

- [Spring in Action 5th Edition](https://www.amazon.com/Spring-Action-Craig-Walls/dp/1617294942)
- [Spring Data JPA - Reference Documentation](https://docs.spring.io/spring-data/jpa/docs/2.1.9.RELEASE/reference/html/#specifications)
- [REST Query Language with Spring Data JPA Specifications](https://www.baeldung.com/rest-api-search-language-spring-data-specifications)
