package org.nkcoder.admin.customized;

import java.time.LocalDateTime;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

@Component
public class MyHealthIndicator implements HealthIndicator {

  @Override
  public Health health() {
    int hour = LocalDateTime.now().getHour();

    if (hour > 0 && hour < 5) {
      return Health.outOfService()
          .status(Status.OUT_OF_SERVICE)
          .withDetail("reason", "I'm out of service between 0am and 5am.")
          .withDetail("hour", hour)
          .build();
    }

    return Health.up().status(Status.UP).withDetail("reason", "All is good.").build();
  }
}
