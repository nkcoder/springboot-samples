# Spring Data Rest

`Spring Data Rest`是基于`Spring MVC`的，将`Spring Data`的接口对外export成REST操作。

## 添加依赖

如果是Spring Boot项目：

    implementation("org.springframework.boot:spring-boot-starter-data-rest:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")
      
如果是gradle项目：

    implementation("org.springframework.data:spring-data-rest-webmvc:3.1.9.RELEASE")
    

## 定义Repository

```java
@RepositoryRestResource(collectionResourceRel = "players", path = "players")
public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {

  List<Player> findByName(@Param("name") String name);

}
``` 

因为这里的domain类是`Player`，`Spring Data Rest`将暴露一组接口集合，path前缀是`/players`。

`RepositoryRestResource`不是必须的，*collectionResourceRel*表示结果中集合值的字段名，*path*表示请求的路径，如果不设置，
默认值都是*players*。如下示例：

    http get :8080/players
    http get :8080/players/3
    http delete :8080/players/5
    http put :8080/players/1 team=LA2
    http patch :8080/players/1 team=LA name=Kobe bornAt=2019-07-27T10:01:01
    http get :8080/players/search/findByName name==Kobe
    (equal: curl 'http://localhost:8080/players/search/findByName?name=Kobe')

其中最后一个是通过自定义方法`findByName`来查询的，访问路径就是/search/方法名，以及参数名。

修改baseUri，在`application.yml`中配置：

    spring.data.rest.basePath=/api
    






