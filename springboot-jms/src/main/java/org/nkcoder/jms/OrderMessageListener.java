package org.nkcoder.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderMessageListener {

  @JmsListener(destination = DestinationConfig.ORDER_QUEUE)
  public void listen(Order order) {
    log.info("listener receive a message: {} from queue: {}", order, DestinationConfig.ORDER_QUEUE);
  }

}
