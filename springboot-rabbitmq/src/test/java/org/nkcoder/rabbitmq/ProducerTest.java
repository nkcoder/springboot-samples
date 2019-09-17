package org.nkcoder.rabbitmq;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProducerTest {

  @Autowired
  private OrderMessageProducer producer;
  @Autowired
  private RabbitTemplate rabbitTemplate;

  @BeforeEach
  public void setup() {
    rabbitTemplate.setReceiveTimeout(3000);
  }

  @Test
  public void shouldSendMessageToDirectExchange() {
    Order order = new Order("1", "order1");
    producer.sendToDirect(order, RabbitmqConfig.ORDER_QUEUE);

    Order message = (Order) rabbitTemplate.receiveAndConvert(RabbitmqConfig.ORDER_QUEUE);
    assertThat(message).isNotNull();
    assertThat(message).isEqualTo(order);

  }

  @Test
  public void shouldSendMessageToTopicExchange() {
    Order order = new Order("2", "order2");
    producer.sendToTopic(order, "order.a");

    Order message = (Order) rabbitTemplate.receiveAndConvert(RabbitmqConfig.ORDER_QUEUE);
    assertThat(message).isNotNull();
    assertThat(message).isEqualTo(order);

    Order message2 = (Order) rabbitTemplate.receiveAndConvert(RabbitmqConfig.ORDER_QUEUE_BAK);
    assertThat(message2).isNotNull();
    assertThat(message2).isEqualTo(order);
  }

}
