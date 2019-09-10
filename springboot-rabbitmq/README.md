## 环境设置

docker启动rabbitmq，`docker-compose.yml`中相关配置如下：

```yml
  rabbitmq:
    image: "rabbitmq:3.8-rc-management-alpine"
    ports:
      - "15672:15672"
      - "5672:5672"
    labels:
      name: "my-rabbitmq"
    environment:
      RABBITMQ_DEFAULT_USER: "root"
      RABBITMQ_DEFAULT_PASS: "rabbit-pass"
      RABBITMQ_DEFAULT_VHOST: "/"
```

访问控制台：http://localhost:15672

## SpringBoot 集成 RabbitMQ

ActiveMQ 中，消息是直接发送到`queue`中。但是在 RabbitMQ 中，消息首先到达`Exchange`，`Exchange`根据自身的类型、`Exchange`与`Queue`之间的 binding，以及`Routing Key`的值，将消息路由到一个或多个`queue`中。

SpringBoot集成RabbitMQ，只需要添加`spring-boot-starter-amqp`依赖，该依赖会自动配置，并暴露`RabbitTemplate`进行消息的发送与接收：

```gradle
implementation("org.springframework.boot:spring-boot-starter-amqp:$springBootVersion")
```

然后在`application.yml`中配置连接信息:

```yml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: root
    password: rabbit-pass
```

`Exchange`的类型主要有：

- Default：这是一个特殊的`Exchange`，自动创建，所有的`queue`都自动 binding 到该`Exchange`。路由：消息的`routing key`与队列名称相同的队列。
- Direct：消息的`routing key`与队列的`binding key`相同的队列。
- Topic：消息的`routing key`与队列的`binding key`正则匹配的所有队列。
- Fanout：路由到所有binding的`queue`，与`routing key`和`binding key`无关。
- Headers：与`Topic`路由类似，只是匹配的是`header`值，而不是`routing key`。
- Dead letter：所有无法投递的消息

默认的`Exchange`的值是“”，默认的`routing key`的值是“”。默认值可以通过配置修改：

```yml
spring:
  rabbitmq:
    template:
      exchange: default
      routing-key: default
```

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

`RabbitTemplate`提供的消息发送的方法与`JmsTemplate`很类似，分为`send()`与`convertAndSend()`两类：

```java
// Send raw messages
void send(Message message) throws AmqpException;
void send(String routingKey, Message message) throws AmqpException;
void send(String exchange, String routingKey, Message message)
                            throws AmqpException;

// Send messages converted from objects
void convertAndSend(Object message) throws AmqpException;
void convertAndSend(String routingKey, Object message)
                            throws AmqpException;
void convertAndSend(String exchange, String routingKey,
                    Object message) throws AmqpException;

// Send messages converted from objects with post-processing
void convertAndSend(Object message, MessagePostProcessor mPP)
                            throws AmqpException;
void convertAndSend(String routingKey, Object message,
                    MessagePostProcessor messagePostProcessor)
                    throws AmqpException;
void convertAndSend(String exchange, String routingKey,
                    Object message,
                    MessagePostProcessor messagePostProcessor)
                    throws AmqpException;
```

如果方法参数中没有`exchange`参数，则表示发送到默认的`exchange`，如果方法中没有`routingKey`参数，则使用默认的`routingKey`。

```java
rabbitTemplate.convertAndSend(RabbitmqConfig.TOPIC_EXCHANGE, order.getName, order);
```

## 接收消息

RabbitMQ也提供了PULL和PUSH两种方式来接收消息，PULL即通过`receive()`方法主动收取消息，PUSH即通过`@RabbitListener`方式监听消息。

`receive()`相关的方法有：

```java
// Receive messages
Message receive() throws AmqpException;
Message receive(String queueName) throws AmqpException;
Message receive(long timeoutMillis) throws AmqpException;
Message receive(String queueName, long timeoutMillis) throws AmqpException;

// Receive objects converted from messages
Object receiveAndConvert() throws AmqpException;
Object receiveAndConvert(String queueName) throws AmqpException;
Object receiveAndConvert(long timeoutMillis) throws AmqpException;
Object receiveAndConvert(String queueName, long timeoutMillis) throws
     AmqpException;

// Receive type-safe objects converted from messages
<T> T receiveAndConvert(ParameterizedTypeReference<T> type) throws
AmqpException;
<T> T receiveAndConvert(String queueName, ParameterizedTypeReference<T> type)
     throws AmqpException;
<T> T receiveAndConvert(long timeoutMillis, ParameterizedTypeReference<T>
     type) throws AmqpException;
<T> T receiveAndConvert(String queueName, long timeoutMillis,
     ParameterizedTypeReference<T> type)
    throws AmqpException;
```

`ParameterizedTypeReference`表示消息的类型，如果设置该参数，则不需要强制类型转换了，具有更好的类型安全。

但是有一个前提条件，即Message Converter必须是`SmartMessageConverter`的实现，目前只有`Jackson2JsonMessageConverter`满足。

```java
Order order2 = (Order) rabbitTemplate.receiveAndConvert(RabbitmqConfig.ORDER_QUEUE_BAK);
Order order3 = rabbitTemplate.receiveAndConvert(RabbitmqConfig.ORDER_QUEUE,
    new ParameterizedTypeReference<Order>() {
    });
```

还可以通过`Listener`监听接收消息，定义一个 Bean，将其中的方法使用`@RabbitListener`注解即可：

```java
@Component
@Slf4j
public class OrderMessageListener {

  @RabbitListener(queues = {RabbitmqConfig.ORDER_QUEUE, RabbitmqConfig.ORDER_QUEUE_BAK})
  public void receive(Order order) {
    log.info("receive message: {}", order);
  }

}
```

## 参考

- [Spring in Action 5th Edition](https://www.amazon.com/Spring-Action-Craig-Walls/dp/1617294942)