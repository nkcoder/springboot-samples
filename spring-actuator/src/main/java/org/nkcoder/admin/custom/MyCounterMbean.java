package org.nkcoder.admin.custom;

import java.util.concurrent.atomic.AtomicInteger;
import javax.management.Notification;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Component;

@Component
@ManagedResource
public class MyCounterMbean implements NotificationPublisherAware {

  private AtomicInteger counter;
  private NotificationPublisher notificationPublisher;

  public MyCounterMbean() {
    this.counter = new AtomicInteger(0);
  }

  @ManagedAttribute
  public int getCount() {
    return counter.get();
  }

  @ManagedOperation
  public int increment(int delta) {
    int value = counter.addAndGet(delta);
    if (value % 5 == 0) {
      notificationPublisher.sendNotification(new Notification(
          "my.counter",
          this,
          value,
          "current value: " + value
      ));
    }

    return value;
  }

  @Override
  public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
    this.notificationPublisher = notificationPublisher;
  }

}
