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

  public void sendToDefault(Order order) {
    rabbitTemplate.convertAndSend(order);
    rabbitTemplate.convertAndSend("", order);
    rabbitTemplate.convertAndSend("", "", order);

    log.info("send message: {} to default exchange", order);
  }

  public void sendToDirect(Order order) {
    rabbitTemplate.convertAndSend(RabbitmqConfig.DIRECT_EXCHANGE, order.getName(), order);

    log.info("send message: {} to exchange: {}", order, RabbitmqConfig.DIRECT_EXCHANGE);
  }

  public void sendToTopic(Order order) {
    rabbitTemplate.convertAndSend(RabbitmqConfig.TOPIC_EXCHANGE, order.getName(), order);

    log.info("send message: {} to exchange: {}", order, RabbitmqConfig.TOPIC_EXCHANGE);
  }

}
