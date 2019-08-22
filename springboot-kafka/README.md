## 本地启动 Kafka

定义`docker-compose.yml`：

    version: '2'
    services:
      zookeeper:
        image: zookeeper
        ports:
          - "2181:2181"
      kafka:
        image: wurstmeister/kafka
        ports:
          - "9092:9092"
        environment:
          KAFKA_ADVERTISED_HOST_NAME: 192.168.248.53
          KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
        volumes:
          - /var/run/docker.sock:/var/run/docker.sock

然后启动：

    $ docker-compose up

## SpringBoot 集成 Kafka

添加依赖：

    compile group: 'org.springframework.kafka', name: 'spring-kafka', version: "$kafkaVersion"

配置 Kafka 连接：

    spring:
      kafka:
        bootstrap-servers:
        - 192.168.248.53:9092
        template:
          default-topic: order.topic

在发送消息和接收消息之前，需要配置消息的序列化与反序列化。可以通过 Bean 的方式配置，SpringBoot 也提供了通过`application.yml`文件的配置方式，如：

    spring:
      kafka:
        producer:
          key-serializer: org.apache.kafka.common.serialization.StringSerializer
          value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
          properties:
            spring.json.trusted.packages: org.nkcoder.kafka

        consumer:
          key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
          value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
          auto-offset-reset: earliest
          properties:
            spring.json.trusted.packages: org.nkcoder.kafka

序列化与反序列化的接口是：`Serializer/Deserializer`，原始类型都有对应的子类实现，如`StringSerializer/StringDeserializer`, `IntegerSerializer/IntegerDeserializer`, `ByteBufferSerializer/ByteBufferDeserializer`，如果是类类型，则使用：`JsonSerializer/JsonDeserializer`。
另外，如果是自定义类，还需要通过`spring.json.trusted.packages`属性将其加入到可信列表中。

## 发送消息

`KafkaTemplate`提供了一组`send()`方法，因为本身提供了类型化，所以不需要`convertAndSend()`做类型转换。

简单地发送消息，注入`KafkaTemplate()`，调用`send(String topic, @Nullable V data)`方法即可，如：

```java
kafkaTemplate.send("order.topic", order);
kafkaTemplate.sendDefault(order);
```

## 接收消息

接收消息也是通过`@KafkaListener`定义消息监听器，可以仅接收消息，也可以接收带额外信息的`Message`以及`ConsumerRecord`，如：

```java
@KafkaListener(topics = "order.topic", groupId = "group1")
public void receive(Order order) {
  log.info("receive message: {}", order);
}

@KafkaListener(topics = "order.topic", groupId = "group2")
public void receive(Order order, ConsumerRecord<String, Order> record) {
  log.info("receive message: {} from partition: {} with timestamp: {}", order, record.partition(),
      record.timestamp());
}

@KafkaListener(topics = "order.topic", groupId = "group3")
public void receive(Order order, Message<Order> message) {
  MessageHeaders headers = message.getHeaders();
  log.info("receive message: {} from partition: {} with timestamp: {}", order,
      headers.get(KafkaHeaders.RECEIVED_PARTITION_ID),
      headers.get(KafkaHeaders.RECEIVED_TIMESTAMP));
}
```
