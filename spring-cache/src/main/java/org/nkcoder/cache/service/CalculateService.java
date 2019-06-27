package org.nkcoder.cache.service;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CalculateService {

  @Cacheable("one")
  public int calculate(int x, int y) {
    simulateHeavyOperation();
    int result = x * y;
    log.info("result: {}", result);
    return result;
  }

  private void simulateHeavyOperation() {
    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException ex) {
      log.error(ex.getMessage(), ex);
    }
  }

}
