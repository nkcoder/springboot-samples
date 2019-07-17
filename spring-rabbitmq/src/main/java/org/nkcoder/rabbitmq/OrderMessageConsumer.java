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

  public void consume() {
    Order order1 = (Order) rabbitTemplate.receiveAndConvert(RabbitmqConfig.ORDER_QUEUE);
    log.info("receive message: {} from queue ORDER_QUEUE", order1);

    Order order2 = (Order) rabbitTemplate.receiveAndConvert(RabbitmqConfig.ORDER_QUEUE_BAK);
    log.info("receive message: {} from queue ORDER_QUEUE_BAK", order2);

  }

}
