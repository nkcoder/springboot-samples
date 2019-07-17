package org.nkcoder.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

  static final String ORDER_QUEUE = "order.queue";
  static final String STOCK_QUEUE = "stock.queue";

  static final String DIRECT_EXCHANGE = "direct-1";
  static final String TOPIC_EXCHANGE = "topic-1";

  @Bean
  public Queue orderQueue() {
    return new Queue(ORDER_QUEUE, true);
  }

  @Bean
  public Queue stockQueue() {
    return new Queue(STOCK_QUEUE, false);
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
    return BindingBuilder.bind(orderQueue).to(directExchange).with("order");
  }

  @Bean
  public Binding topicBinding(@Qualifier("orderQueue") Queue orderQueue,
      @Qualifier("topicExchange") TopicExchange topicExchange) {
    return BindingBuilder.bind(orderQueue).to(topicExchange).with("order.3");
  }

  @Bean
  public Binding topicBinding2(@Qualifier("stockQueue") Queue stockQueue,
      @Qualifier("topicExchange") TopicExchange topicExchange) {
    return BindingBuilder.bind(stockQueue).to(topicExchange).with("order.#");
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setDefaultReceiveQueue(ORDER_QUEUE);
    return rabbitTemplate;
  }

}
