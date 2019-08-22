package org.nkcoder.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderMessageConsumer {

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

}
