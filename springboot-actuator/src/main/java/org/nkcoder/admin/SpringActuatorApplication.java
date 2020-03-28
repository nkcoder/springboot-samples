package org.nkcoder.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringActuatorApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringActuatorApplication.class, args);
  }

  @Bean
  public InMemoryHttpTraceRepository httpTraceRepository() {
    return new InMemoryHttpTraceRepository();
  }
}
