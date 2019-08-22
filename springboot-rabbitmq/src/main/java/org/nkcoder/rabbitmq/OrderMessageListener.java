package org.nkcoder.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderMessageListener {

  @RabbitListener(queues = {RabbitmqConfig.ORDER_QUEUE, RabbitmqConfig.ORDER_QUEUE_BAK})
  public void receive(Order order) {
    log.info("receive message: {}", order);
  }

}
