package org.nkcoder.config.property;

import java.util.List;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
@PropertySource(value = "classpath:mail-config.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "mail")
public class MailConfig {

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
