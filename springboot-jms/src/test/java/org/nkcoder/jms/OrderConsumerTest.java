package org.nkcoder.jms;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderConsumerTest {

  @Autowired
  private OrderMessageConsumer consumer;

  @Autowired
  private JmsTemplate jmsTemplate;

  @BeforeEach
  public void setup() {
    jmsTemplate.setReceiveTimeout(10000);
  }


  @Test
  public void shouldReceive() {
    Order order = new Order("2", 10);
    jmsTemplate.convertAndSend(order);

    Order orderMsg = consumer.receive();
    assertThat(orderMsg).isEqualTo(order);
  }

  @Test
  public void shouldReceiveAndConvert() {
    Order order = new Order("2", 10);
    jmsTemplate.convertAndSend(DestinationConfig.ORDER_QUEUE, order);

    Order orderMsg = consumer.receiveAndConvert();
    assertThat(orderMsg).isEqualTo(order);
  }

}
