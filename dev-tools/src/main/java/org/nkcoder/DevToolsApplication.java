package org.nkcoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevToolsApplication {

  public static void main(String[] args) {
    System.out.println("main");
    SpringApplication.run(DevToolsApplication.class, args);
  }

}
