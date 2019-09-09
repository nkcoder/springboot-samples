package org.nkcoder.jms;

import static org.assertj.core.api.Assertions.assertThat;

import javax.jms.JMSException;
import javax.jms.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrderProducerTest {

  @Autowired
  private OrderMessageProducer producer;

  @Autowired
  private JmsTemplate jmsTemplate;

  @Autowired
  @Qualifier("simpleMessageConverter")
  private MessageConverter messageConverter;


  @BeforeEach
  public void setup() {
    jmsTemplate.setReceiveTimeout(10000);
  }

  @Test
  public void shouldReceiveMessages_whenSend() throws JMSException {
    Order order = new Order("1", 5);
    producer.send(order);

    Message message = jmsTemplate.receive();
    assertThat(message).isNotNull();

    Order orderMessage = (Order) messageConverter.fromMessage(message);
    assertThat(orderMessage).isNotNull();
    assertThat(orderMessage).isEqualTo(order);

    Message message2 = jmsTemplate.receive(DestinationConfig.ORDER_QUEUE);
    Message message3 = jmsTemplate.receive(DestinationConfig.ORDER_QUEUE);
    assertThat(message2).isNotNull();
    assertThat(message3).isNotNull();

  }

  @Test
  public void shouldReceiveMessages_whenConvertAndSend() {
    Order order = new Order("1", 5);
    producer.convertAndSend(order);

    Order orderMsg1 = (Order) jmsTemplate.receiveAndConvert();
    assertThat(orderMsg1).isNotNull();
    assertThat(orderMsg1).isEqualTo(order);

    Order orderMsg2 = (Order) jmsTemplate.receiveAndConvert(DestinationConfig.ORDER_QUEUE);
    Order orderMsg3 = (Order) jmsTemplate.receiveAndConvert(DestinationConfig.ORDER_QUEUE);
    assertThat(orderMsg2).isEqualTo(order);
    assertThat(orderMsg3).isEqualTo(order);

  }

}
