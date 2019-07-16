package org.nkcoder.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderMessageProducer {

  private final JmsTemplate jmsTemplate;
  private final Destination orderQueue;

  public OrderMessageProducer(JmsTemplate jmsTemplate, Destination orderQueue) {
    this.jmsTemplate = jmsTemplate;
    this.orderQueue = orderQueue;
  }

  public void send(Order order) {
    jmsTemplate.send(session -> session.createObjectMessage(order));
    jmsTemplate.send(orderQueue, session -> session.createObjectMessage(order));
    jmsTemplate.send(QueueConfig.ORDER_QUEUE, session -> session.createObjectMessage(order));

    log.info("send order message: {} to queue", order);
  }

  public void convertAndSend(Order order) {
    jmsTemplate.convertAndSend(orderQueue);
    jmsTemplate.convertAndSend(orderQueue, order);
    jmsTemplate.convertAndSend(QueueConfig.ORDER_QUEUE, order);

    log.info("convert and send message: {} to queue", order);
  }

  public void convertAndSendWithPostProcess(Order order) {
    jmsTemplate.convertAndSend(orderQueue, order, this::postProcessor);

    log.info("convert and send message with post processor: {} to queue.", order,
        ((ActiveMQQueue) orderQueue).getQueueName());
  }

  private Message postProcessor(Message message) throws JMSException {
    message.setStringProperty("X_ORDER_SOURCE", "WEB");
    return message;
  }

}
