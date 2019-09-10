Kafka一般以cluster模式运行，提供较好的扩展性。一个topic被分区到cluster的多个节点上，cluster中的每个节点作为一个或多个topic的leader，负责该topic的数据以及复制。

## 环境配置

kafka依赖zookker，在`docker-compose.yml`中的相关配置如下：

```yml
  zookeeper:
    image: zookeeper
    ports:
      - "2181:2181"
    networks:
      - backend

  kafka:
    image: wurstmeister/kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: 192.168.248.53
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
    networks:
      - backend
    depends_on:
      - zookeeper
```

## SpringBoot 集成 Kafka

首先添加`spring-kafka`依赖，该依赖会触发SpringBoot的自动配置，并提供`KafkaTemplate`用于消息的接收与发送：


```gradle
implementation group: 'org.springframework.kafka', name: 'spring-kafka', version: "$kafkaVersion"
```

配置 Kafka 连接：

```yml
spring:
  kafka:
    bootstrap-servers:
    - 192.168.248.53:9092
    template:
      default-topic: order.topic
```

其中`spring.kafka.bootstrap-servers`表示kafka集群的地址。

在发送消息和接收消息之前，需要配置消息的序列化与反序列化。可以通过 Bean 的方式配置，SpringBoot 也提供了通过`application.yml`文件的配置方式，如：

```yml
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
```

序列化与反序列化的接口是：`Serializer/Deserializer`。

原始类型都有对应的子类实现：

- StringSerializer/StringDeserializer 
- IntegerSerializer/IntegerDeserializer, 
- ByteBufferSerializer/ByteBufferDeserializer

如果是类类型，则使用：

- JsonSerializer/JsonDeserializer

另外，如果是自定义类，还需要通过`spring.json.trusted.packages`属性将其加入到可信列表中，如：

```yml
spring:
  kafka:
    producer:
      properties:
        spring.json.trusted.packages: org.nkcoder.kafka
    consumer:
      properties:
        spring.json.trusted.packages: org.nkcoder.kafka
```

## 发送消息

`KafkaTemplate`提供了发送消息的方法，方法本身带泛型，因此不需要做类型转换。

```java
ListenableFuture<SendResult<K, V>> send(String topic, V data);
ListenableFuture<SendResult<K, V>> send(String topic, K key, V data);
ListenableFuture<SendResult<K, V>> send(String topic,
                                  Integer partition, K key, V data);
ListenableFuture<SendResult<K, V>> send(String topic,
                  Integer partition, Long timestamp, K key, V data);

ListenableFuture<SendResult<K, V>> send(ProducerRecord<K, V> record);
ListenableFuture<SendResult<K, V>> send(Message<?> message);

ListenableFuture<SendResult<K, V>> sendDefault(V data);
ListenableFuture<SendResult<K, V>> sendDefault(K key, V data);
ListenableFuture<SendResult<K, V>> sendDefault(Integer partition,
                                               K key, V data);
ListenableFuture<SendResult<K, V>> sendDefault(Integer partition,
                                     Long timestamp, K key, V data);
```

参数中，`key`和`partition`是可选的，`topic`和`data`是必须的，其中`sendDefault`表示发送到默认的topic，需要配置：

```yml
spring.template.default-topic: order.topic
```

## 接收消息

Kafka只能通过Listener方式接收消息，也是通过`@KafkaListener`定义消息监听器。

如果需要从消息中获取额外的元数据信息，方法可以额外接受一个`Message`以及`ConsumerRecord`参数，如：

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
