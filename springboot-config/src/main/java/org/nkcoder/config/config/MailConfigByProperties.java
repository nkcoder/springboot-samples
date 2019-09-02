package org.nkcoder.config.config;

import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ConfigurationProperties(prefix = "mail2")
@PropertySource(value = "classpath:mail-config.properties")
public class MailConfigByProperties {

  private String host;
  private int port;
  private String from;
  private List<String> defaultRecipients;
  private Map<String, String> additionalHeaders;
  private Credentials credentials;
  private Map<String, Credentials> memberCredentials;

  @Data
  public static class Credentials {
    private String name;
    private String password;
  }
}
