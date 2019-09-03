# Spring Data Rest

`Spring Data Rest`是基于`Spring MVC`的，将`Spring Data`的接口对外export成REST操作。并且支持Hypermedia。

## Hypermedia

Hypermedia是在REST的JSON结果中嵌入hyperlinks，与传统的REST接口相比主要区别是，在返回结果中会多一个`_links`字段，包含了当前资源以及包含的嵌套资源的访问URL，不需要询问服务端，客户端即可知道如何进一步访问资源。以下为hypermedia的response：

```json
{
  "_embedded" : {
    "players": [{
      "name" : "Heinsohn",
      "teamId" : 2,
      "joinAt" : "1970-03-01",
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/api/players/8"
        },
        "player" : {
          "href" : "http://localhost:8080/api/players/8"
        }
      }
    }]
  },
  "_links" : {
    "self" : {
      "href" : "http://localhost:8080/api/players{?page,size,sort}",
      "templated" : true
    },
    "profile" : {
      "href" : "http://localhost:8080/api/profile/players"
    },
    "search" : {
      "href" : "http://localhost:8080/api/players/search"
    }
  },
  "page" : {
    "size" : 20,
    "totalElements" : 1,
    "totalPages" : 1,
    "number" : 0
  }
}
```

在Spring中，HATEOAS项目用于支持hypermedia。因此，如果需要启用Hypermedia，需要添加依赖`spring-boot-starter-hateoas`。

```gradle
implementation('org.springframework.boot':'spring-boot-starter-hateoas':'2.1.7.RELEASE')
```

Hateoas提供了两种资源，`Resource`以及`Resources`，分别表示单个资源以及资源集合。

可以通过`ControllerLinkBuilder`方便地构建links：

```java
@GetMapping("/players/by-team")
public Resources<Resource<Player>> getByTeamAndJoinAtDesc(
  @RequestParam("teamId") Integer teamId) {
  Iterable<Player> playersIterable = playerRepository.findAll(Sort.by(Order.desc("joinAt")));
  List<Player> players = StreamSupport.stream(playersIterable.spliterator(), false)
      .filter(p -> p.getTeamId().equals(teamId))
      .collect(Collectors.toList());

  Resources<Resource<Player>> playerResources = Resources.wrap(players);
  playerResources.add(
      linkTo(methodOn(PlayerController.class).getByTeamAndJoinAtDesc(teamId)).withRel("by-team"));
  return playerResources;
}
```

## Spring Data Rest

Spring Data Rest可以为Spring Data创建的Repository自动创建REST API，并且支持Hypermedia。只需要添加依赖`spring-boot-starter-data-rest`即可：

```gradle
implementation("org.springframework.boot:spring-boot-starter-data-rest:$springBootVersion")
implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
``` 

比如下面的Repository，会自动export所有CRUD的API接口。默认的path为entity类的复数形式，如这里的路径默认为`/players`：

```java
public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {}
``` 

访问示例：

```bash
http get :8080/players
http get :8080/players/3
http delete :8080/players/5
http put :8080/players/1 team=LA2
http patch :8080/players/1 team=LA name=Kobe bornAt=2019-07-27T10:01:01
```

### 修改path

如果需要修改默认的path，可以通过两种方式，一种是在entity上使用`@RestResource(rel = "players", path = "players")`注解，另一种方式是在Repository上使用`@RepositoryRestResource(collectionResourceRel = "players", path = "players")`注解。

其中`collectionResourceRel`表示结果中集合值的字段名，`path`表示请求的路径。

除了Repository默认的方法，也支持自定义的方法，不过访问需要带前缀，如：

```java
@RepositoryRestResource(collectionResourceRel = "players", path = "players")
public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {
  List<Player> findByName(@Param("name") String name);
}
```

### 自定义查询

访问的path需要`/search`前缀，如：

```bash
http get :8080/players/search/findByName name==Kobe
(equal: curl 'http://localhost:8080/players/search/findByName?name=Kobe')
```

`spring.data.rest.basePath`用于修改baseUri，在`application.yml`中配置：

```yml
spring.data.rest.basePath=/api
```

### 分页与排序

对于集合类型的接口，默认支持分页和排序，关键字为`page,size,sort`，示例如下：

```bash
http ':8080/api/players?page=0&size=5'
http ':8080/api/players?page=0&sort=joinAt,desc'
```

## 参考

- [Spring in Action 5th Edition](https://www.amazon.com/Spring-Action-Craig-Walls/dp/1617294942)




