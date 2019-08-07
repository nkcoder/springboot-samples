package org.nkcoder.admin.custom;

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

    if (hour < 12) {
      return Health.outOfService()
          .status(Status.OUT_OF_SERVICE)
          .withDetail("reason", "I'm out of service after lunch time.")
          .withDetail("hour", hour)
          .build();
    }

    if (Math.random() < 0.1) {
      return Health.down()
          .status(Status.DOWN)
          .withDetail("reason", "I'm down by 10%.")
          .build();
    }

    return Health.up().status(Status.UP).withDetail("reason", "All is good.").build();
  }

}
