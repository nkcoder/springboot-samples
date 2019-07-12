package org.nkcoder.config.controller;

import org.nkcoder.config.service.ConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * use https
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

  private final ConfigService configService;

  public HelloController(ConfigService configService) {
    this.configService = configService;
  }

  @GetMapping
  public String hello() {
    return "hello, world";
  }

  @GetMapping("/config")
  public String getConfigInfo() {
    return configService.getConfigInfo();
  }


}
