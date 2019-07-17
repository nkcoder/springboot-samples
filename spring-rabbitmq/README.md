## 本地启动 RabbitMQ

先定义`docker-compose.yml`文件，然后启动：

    $ docker-compose up

访问控制台：http://localhost:15672

## SpringBoot 配置 RabbitMQ

在`build.gradle`中添加依赖:

    dependencies {
        compile group: 'org.springframework.boot', name: 'spring-boot-starter-amqp', version: "$springBootVersion"
    }

在`application.yml`中配置连接信息:

    spring:
      rabbitmq:
        host: localhost
        port: 5672
        username: root
        password: rabbit-pass

## RabbitMQ 简单介绍

ActiveMQ 中，消息是直接发送到`queue`中。但是在 RabbitMQ 中，消息首先到达`Exchange`，`Exchange`根据自身的类型、`Exchange`与`Queue`之间的 binding，以及`Routing Key`的值，将消息路由到一个或多个`queue`中。

`Exchange`的类型主要有：

- Default：这是一个特殊的`Exchange`，自动创建，所有的`queue`都自动 binding 到该`Exchange`。路由到与`routing key`名称相同的`queue`。
- Direct：路由到与`routing key`名称相同的`queue`。
- Topic：路由到`routing key`名称正则匹配的`queue`。
- Fanout：路由到所有绑定的`queue`，与`routing key`无关。
- Headers：与`Topic`类似，只是匹配的是`header`值，而不是`routing key`。
- Dead letter：所有无法投递的消息

默认的`Exchange`的值是“”，默认的`routing key`的值是“”。默认值可以通过配置修改：

    spring:
      rabbitmq:
        template:
          exchange: default
          routing-key: default

需要将`Queue`、`Exchange`、`Binding`等定义为 Bean，如：

```java
@Configuration
public class RabbitmqConfig {

  public static final String ORDER_QUEUE = "order.queue";
  public static final String TOPIC_EXCHANGE = "topic-1";

  @Bean
  public Queue orderQueue() {
    return new Queue(ORDER_QUEUE, true);
  }

  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(TOPIC_EXCHANGE, true, false);
  }

  @Bean
  public Binding topicBinding(Queue queue, TopicExchange topicExchange) {
    return BindingBuilder.bind(stockQueue).to(topicExchange).with("order.#");
  }

}
```

## 发送消息
