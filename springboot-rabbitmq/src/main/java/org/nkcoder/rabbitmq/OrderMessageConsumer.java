package org.nkcoder.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderMessageConsumer {

  private final RabbitTemplate rabbitTemplate;

  public OrderMessageConsumer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public Order consume(String queue) {
    Order message = (Order) rabbitTemplate.receiveAndConvert(queue);
    log.info("receive message: {} from queue ORDER_QUEUE", message);
    return message;
  }

}
