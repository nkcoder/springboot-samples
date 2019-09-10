package org.nkcoder.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(name = "listener.enable",havingValue = "true")
public class OrderMessageListener {

  @RabbitListener(queues = {RabbitmqConfig.ORDER_QUEUE, RabbitmqConfig.ORDER_QUEUE_BAK})
  public void receive(Order order) {
    log.info("receive message: {}", order);
  }

}
