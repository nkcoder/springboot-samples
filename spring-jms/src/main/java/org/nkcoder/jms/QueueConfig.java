package org.nkcoder.jms;

import javax.jms.Destination;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

@Configuration
public class QueueConfig {

  public static final String ORDER_QUEUE = "order.queue";

  @Bean
  public Destination orderQueue() {
    return new ActiveMQQueue(ORDER_QUEUE);
  }

  @Bean
  public MessageConverter messageConverter() {
    return new SimpleMessageConverter();
  }

}
