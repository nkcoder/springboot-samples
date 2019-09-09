package org.nkcoder.jms;

import java.util.HashMap;
import javax.jms.Destination;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

@Configuration
public class DestinationConfig {

  static final String ORDER_QUEUE = "order.queue";

  @Bean
  public Destination orderQueue() {
    return new ActiveMQQueue(ORDER_QUEUE);
  }

  @Bean("simpleMessageConverter")
  public MessageConverter simpleMessageConverter() {
    return new SimpleMessageConverter();
  }

  @Bean("jsonMessageConverter")
  public MessageConverter jsonMessageConverter() {
    MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
    messageConverter.setTypeIdPropertyName("_typeId");
    HashMap<String, Class<?>> typeIdMappings = new HashMap<>();
    typeIdMappings.put("order", Order.class);
    messageConverter.setTypeIdMappings(typeIdMappings);
    return messageConverter;
  }

}
