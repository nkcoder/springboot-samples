package org.nkcoder.config.config;

import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@PropertySource(value = "classpath:mail-config.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "mail")
public class MailConfigByYml {

  @NotEmpty
  private String host;
  @NotEmpty
  private int port;
  @NotEmpty
  private String from;
  @NotEmpty
  private List<String> defaultRecipients;
  private Map<String, String> additionalHeaders;
  @NotNull
  private Credentials credentials;
  private Map<String, Credentials> memberCredentials;

  @Data
  public static class Credentials {

    private String name;
    private String password;
  }

}
