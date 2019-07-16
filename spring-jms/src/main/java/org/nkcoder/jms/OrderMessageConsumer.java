package org.nkcoder.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderMessageConsumer {

  private final JmsTemplate jmsTemplate;
  private final MessageConverter messageConverter;

  public OrderMessageConsumer(JmsTemplate jmsTemplate,
      MessageConverter messageConverter) {
    this.jmsTemplate = jmsTemplate;
    this.messageConverter = messageConverter;
  }

  public Order receive() {
    Message message = jmsTemplate.receive();
    try {
      Order order = (Order) messageConverter.fromMessage(message);
      log.info("get message: {} from default queue", order);
      return order;
    } catch (JMSException ex) {
      log.error(ex.getMessage(), ex);
      throw new RuntimeException("convert from message failed", ex);
    }

  }

  public Order receiveAndConvert() {
    Order order = (Order) jmsTemplate.receiveAndConvert(QueueConfig.ORDER_QUEUE);
    log.info("get message: {} from queue: {}", order, QueueConfig.ORDER_QUEUE);
    return order;
  }

}
