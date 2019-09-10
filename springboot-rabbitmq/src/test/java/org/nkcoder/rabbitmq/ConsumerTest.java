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
public class ConsumerTest {

  @Autowired
  private RabbitTemplate rabbitTemplate;
  @Autowired
  private OrderMessageConsumer consumer;

  @BeforeEach
  public void setup() {
    rabbitTemplate.setReceiveTimeout(3000);
    cleanQueue();
  }

  private void cleanQueue() {
    Order order = null;
    do {
      order = consumer.consume(RabbitmqConfig.ORDER_QUEUE);
    } while (order != null);

    do {
      order = consumer.consume(RabbitmqConfig.ORDER_QUEUE_BAK);
    } while (order != null);
  }

  @Test
  public void shouldReceiveMessagesFromQueue() {
    Order order = new Order("3", "order3");
    rabbitTemplate.convertAndSend(RabbitmqConfig.TOPIC_EXCHANGE, "order.b", order);

    Order message1 = consumer.consume(RabbitmqConfig.ORDER_QUEUE);
    Order message2 = consumer.consume(RabbitmqConfig.ORDER_QUEUE_BAK);

    assertThat(message1).isNotNull();
    assertThat(message2).isNotNull();
    assertThat(message1).isEqualTo(order);
    assertThat(message2).isEqualTo(message1);
  }

}
