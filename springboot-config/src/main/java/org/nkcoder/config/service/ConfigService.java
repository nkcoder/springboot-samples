package org.nkcoder.config.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.nkcoder.config.property.MailConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConfigService {

  private final String appName;
  private final MailConfig mailConfig;
  private final ObjectMapper objectMapper;

  public ConfigService(@Value("${app.name}") String appName, MailConfig mailConfig,
      ObjectMapper objectMapper) {
    this.appName = appName;
    this.mailConfig = mailConfig;
    this.objectMapper = objectMapper;
  }

  public String getConfigInfo() {
    log.info("appName: {}, mail config: {}", appName, mailConfig.toString());
    try {
      return objectMapper.writeValueAsString(mailConfig.toString());
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
      throw new RuntimeException("config error");
    }
  }

}
