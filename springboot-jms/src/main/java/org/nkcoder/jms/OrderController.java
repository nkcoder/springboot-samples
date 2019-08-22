package org.nkcoder.jms;

import java.util.Random;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

  private final OrderMessageProducer messageProducer;
  private final OrderMessageConsumer messageConsumer;

  public OrderController(OrderMessageProducer messageProducer,
      OrderMessageConsumer messageConsumer) {
    this.messageProducer = messageProducer;
    this.messageConsumer = messageConsumer;
  }

  @GetMapping("/produce")
  public void produce() {
    Random random = new Random();
    final int bound = 1000;
    messageProducer.convertAndSend(newOrder(UUID.randomUUID(), random.nextInt(bound)));
    messageProducer
        .convertAndSendWithPostProcess(newOrder(UUID.randomUUID(), random.nextInt(bound)));
  }

  @GetMapping("/consume")
  public Order consume() {
    return messageConsumer.receiveAndConvert();
  }

  private Order newOrder(UUID id, int count) {
    return new Order(id.toString(), count);
  }
}
