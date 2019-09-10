package org.nkcoder.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

  static final String ORDER_QUEUE = "order.queue";
  static final String ORDER_QUEUE_BAK = "order.queue.bak";

  static final String DIRECT_EXCHANGE = "direct-1";
  static final String TOPIC_EXCHANGE = "topic-1";

  @Bean
  public Queue orderQueue() {
    return new Queue(ORDER_QUEUE, true);
  }

  @Bean
  public Queue orderQueueBak() {
    return new Queue(ORDER_QUEUE_BAK, false);
  }

  @Bean
  public DirectExchange directExchange() {
    return new DirectExchange(DIRECT_EXCHANGE, true, true);
  }

  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange(TOPIC_EXCHANGE, true, false);
  }

  @Bean
  public Binding directBinding(@Qualifier("orderQueue") Queue orderQueue,
      @Qualifier("directExchange") DirectExchange directExchange) {
    return BindingBuilder.bind(orderQueue).to(directExchange).with("order.queue");
  }

  @Bean
  public Binding topicBinding(@Qualifier("orderQueue") Queue orderQueue,
      @Qualifier("topicExchange") TopicExchange topicExchange) {
    return BindingBuilder.bind(orderQueue).to(topicExchange).with("order.#");
  }

  @Bean
  public Binding topicBinding2(@Qualifier("orderQueueBak") Queue orderQueue,
      @Qualifier("topicExchange") TopicExchange topicExchange) {
    return BindingBuilder.bind(orderQueue).to(topicExchange).with("order.");
  }

  /**
   * this bean is needed is send message to default queue.
   */
  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setDefaultReceiveQueue(ORDER_QUEUE);
    return rabbitTemplate;
  }

  @Bean
  public MessageConverter messageConverter() {
    return new SimpleMessageConverter();
  }

}
