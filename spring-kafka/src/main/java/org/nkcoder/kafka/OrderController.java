package org.nkcoder.kafka;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

  private final OrderMessageProducer orderMessageProducer;

  public OrderController(OrderMessageProducer orderMessageProducer) {
    this.orderMessageProducer = orderMessageProducer;
  }

  @PostMapping("/produce")
  public void produce() {
    orderMessageProducer.send(new Order("id", "name"));
  }

}
