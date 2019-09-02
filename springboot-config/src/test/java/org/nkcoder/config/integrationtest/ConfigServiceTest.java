package org.nkcoder.config.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nkcoder.config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ConfigServiceTest {

  @Autowired
  private ConfigService configService;

  @Test
  public void shouldReturnAppNameInConfigFile_whenGetAppName() {
    String appName = configService.getAppName();

    assertThat(appName).isEqualTo("spring-config-demo");
  }

  @Test
  public void shouldReturnMailConfig() {
    List<String> recipients = configService.getMailRecipients();

    assertThat(recipients).isNotNull();
    assertThat(recipients.size()).isEqualTo(2);
    assertThat(recipients).hasSameElementsAs(List.of("111@test.com", "222@test.com"));

  }

}
