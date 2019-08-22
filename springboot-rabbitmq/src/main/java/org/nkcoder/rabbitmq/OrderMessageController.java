package org.nkcoder.rabbitmq;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderMessageController {

  private final OrderMessageProducer orderMessageProducer;
  private final OrderMessageConsumer orderMessageConsumer;

  public OrderMessageController(OrderMessageProducer orderMessageProducer,
      OrderMessageConsumer orderMessageConsumer) {
    this.orderMessageProducer = orderMessageProducer;
    this.orderMessageConsumer = orderMessageConsumer;
  }

  @PostMapping("/produce")
  @ResponseStatus(HttpStatus.CREATED)
  public void produce() {
//    orderMessageProducer.sendToDirect(newOrder("order"));
    orderMessageProducer.sendToTopic(newOrder("order.3"));
  }

  @GetMapping("/consume")
  public void consume() {
    orderMessageConsumer.consume();
  }

  private Order newOrder(String name) {
    return new Order(UUID.randomUUID().toString(), name);
  }

}
