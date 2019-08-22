## 在docker中运行activemq-Artemis

    $ docker pull vromero/activemq-artemis:latest-alpine
    $ docker run -it --rm -e ARTEMIS_USERNAME=root -e ARTEMIS_PASSWORD=123admin -p 8161:8161 -p 61616:61616 vromero/activemq-artemis:latest-alpine
  
console:

    http://localhost:8161/console/login
    
## 消息发送

add dependency in `build.gradle`:

    dependencies {
        implementation group: 'org.springframework.boot', name: 'spring-boot-starter-artemis', version: "$springBootVersion"
    }
    
add configuration for MQ in `application.yml`:

    spring:
      artemis:
        host: localhost
        port: 61616
        user: root
        password: 123admin

Spring Boot JMS提供了`JmsTemplate`的Bean，注入并使用即可。

发送消息的接口主要：

```java
void send(MessageCreator messageCreator) throws JmsException;
void send(Destination destination, MessageCreator messageCreator) throws JmsException;
void send(String destinationName, MessageCreator messageCreator) throws JmsException;

void convertAndSend(Object message) throws JmsException;
void convertAndSend(Destination destination, Object message) throws JmsException;
void convertAndSend(String destinationName, Object message) throws JmsException;

void convertAndSend(Object message, MessagePostProcessor postProcessor)
  throws JmsException;
void convertAndSend(Destination destination, Object message, MessagePostProcessor postProcessor)
  throws JmsException;
void convertAndSend(String destinationName, Object message, MessagePostProcessor postProcessor)
  throws JmsException;

```

第一组`send()`方法，发送的是消息的创建逻辑，即`MessageCreator`，调用`session`将消息内容封装为`Message`。
第一个方法表示将消息发送给默认队列，第二个和第三个方法表示将消息发送到对应的队列。

默认队列需要配置，在`application.yml`中配置如下：

    spring:
      jms:
        template:
          default-destination: test

第二组方法发送的是消息内容，`JmsTemplate`在内部通过`MessageConverter`把会把消息体转成`Message`，然后调用第一组方法`send()`；
第三组方法发送的也是消息本身，并且支持`MessagePostProcessor`，用于在发送前对消息做一些额外处理，比如：

```java
jmsTemplate.convertAndSend(orderQueue, message -> {
  message.setStringProperty("X_ORDER_SOURCE", "IOS");
  return message;
});
```

## 消息接收

接收消息分为两种模式，`PULL`和`PUSH`。

`PULL`：消费者主动去拉取消息，如果没有消息，带超时阻塞，比较适合消费者希望主动控制消息的消费。
`PUSH`: 消费者监听在队列上，有消息时，可以接收到，比较适合可以快速处理大量的消息。

JMS提供的消息接收方法都是基于`PULL`模式的，主要方法有：

```java
Message receive() throws JmsException;
Message receive(Destination destination) throws JmsException;
Message receive(String destinationName) throws JmsException;

Object receiveAndConvert() throws JmsException;
Object receiveAndConvert(Destination destination) throws JmsException;
Object receiveAndConvert(String destinationName) throws JmsException;
```

如果使用第一组`receive()`方法，则需要使用`MessageConverter`将Message转换为对应的消息模型，如：

```java
private final MessageConverter messageConverter;

Message message = jmsTemplate.receive();
Order order = (Order) messageConverter.fromMessage(message);
```

使用`@JmsListener`，可以实现基于`PUSH`模型的消息消费，如：

```java
@Component
public class OrderMessageListener {

  @JmsListener(destination = DestinationConfig.ORDER_QUEUE)
  public void listen(Order order) {
    log.info("listener receive a message: {} from queue: {}", order, DestinationConfig.ORDER_QUEUE);
  }

}
```











    

  
