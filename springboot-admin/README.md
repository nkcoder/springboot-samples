Spring Boot Admin 是前端 GUI 项目，将 Actuator 提供的接口以更友好的方式展现。

包括 client 和 server，每一个 Spring Boot 项目都是 client，需要部署一个单独的 Spring Boot 项目作为 server。server 将所有 client 的数据收集起来，并提供基于 Actuator 接口的操作。

client 要注册到 server，有两种方式，第一种是每一个 client 配置 sever 的地址，主动注册，另一种是 server 通过 Eureka 服务注册去发现 client。

## server 端配置

首先需要添加`spring-boot-admin-starter-server`依赖：

```gradle
implementation("de.codecentric:spring-boot-admin-starter-server:$springAdminVersion")
```

然后在启动类（即使用`@SpringBootApplication`注解的类）上添加注解`@EnableAdminServer`即可。

```java
@SpringBootApplication
@EnableAdminServer
public class SpringAdminApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringAdminApplication.class, args);
  }

}
```

如果需要增加安全控制，使用`spring-boot-starter-security`根据需要配置即可。

如果采用 Eureka 服务注册，应该禁止 server 自身被注册为服务，可以通过配置属性实现：

```text
eureka:
  client:
    register-with-eureka: false
```

server 启动后，访问`http://localhost:8080`即可看到 GUI 界面。

## client 端配置

为 Spring Boot 应用添加`spring-boot-admin-starter-client`依赖使其成为 client：

```gradle
implementation "de.codecentric:spring-boot-admin-starter-client:\$springBootAdminVersion"
```

主动注册方式，需要配置下 server 的地址：

```text
spring:
  application:
    name: spring-actuator-demo
  boot:
    admin:
      client:
        url: http://localhost:9090
```

最好将`spring.boot.admin.client.url`配置在`Spring Cloud Config Server`服务中。

如果采用 Eureka 注册方式，client 和 server 添加 Eureka 依赖后，通过服务发现自动注册。

如果 client 启用并配置了`spring-boot-starter-security`，则需要将认证信息发给 server。

如果是主动注册的方式，配置如下：

```text
spring:
  boot:
    admin:
      client:
        instance:
          metadata:
            user.name: ${spring.security.user.name}
            user.password: ${spring.security.user.password}
```

如果通过 Eureka 方式注册，配置如下：

```text
eureka:
  instance:
    metadata-map:
      user.name: admin
      user.password: password
```

Actuator 暴露的接口，在 Spring Boot Admin 中都可以查看或操作。
