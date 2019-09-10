package org.nkcoder.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderMessageProducer {

  private final RabbitTemplate rabbitTemplate;

  public OrderMessageProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  public void sendToDirect(Order order, String routingKey) {
    rabbitTemplate.convertAndSend(RabbitmqConfig.DIRECT_EXCHANGE, routingKey, order);

    log.info("send message: {} to exchange: {}", order, RabbitmqConfig.DIRECT_EXCHANGE);
  }

  public void sendToTopic(Order order, String routingKey) {
    rabbitTemplate.convertAndSend(RabbitmqConfig.TOPIC_EXCHANGE, routingKey, order);

    log.info("send message: {} to exchange: {}", order, RabbitmqConfig.TOPIC_EXCHANGE);
  }

}
