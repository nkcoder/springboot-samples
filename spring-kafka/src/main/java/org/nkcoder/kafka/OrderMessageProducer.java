package org.nkcoder.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderMessageProducer {

  private final KafkaTemplate<String, Order> kafkaTemplate;

  public OrderMessageProducer(
      KafkaTemplate<String, Order> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(Order order) {
//    kafkaTemplate.send("order.topic", order);

    kafkaTemplate.sendDefault(order);

    log.info("send message: {} to topic: order.topic", order);
  }

}
