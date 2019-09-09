package org.nkcoder.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConditionalOnProperty(value = "listener.enable", havingValue = "true")
public class OrderMessageListener {

  @JmsListener(destination = DestinationConfig.ORDER_QUEUE)
  public void listen(Order order) {
    log.info("listener receive a message: {} from queue: {}", order, DestinationConfig.ORDER_QUEUE);
  }

}
