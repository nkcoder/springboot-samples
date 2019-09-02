package org.nkcoder.config.controller;

import java.util.List;
import org.nkcoder.config.service.ConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * use https
 */
@RestController
@RequestMapping("/config")
public class ConfigController {

  private final ConfigService configService;

  public ConfigController(ConfigService configService) {
    this.configService = configService;
  }

  @GetMapping("/app-name")
  public String getAppName() {
    return configService.getAppName();
  }

  @GetMapping("/mail")
  public List<String> getMailRecipients() {
    return configService.getMailRecipients();
  }


}
