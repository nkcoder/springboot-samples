package org.nkcoder.config.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ExtendWith(SpringExtension.class)
@Disabled("https endpoint not work, need to fix")
public class ConfigControllerTest {


  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  public void shouldReturnAppName() {
    ResponseEntity<String> appNameResponse = testRestTemplate
        .getForEntity("https:/localhost:8443/config/app-name", String.class);

    assertThat(appNameResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(appNameResponse.getBody()).isEqualTo("spring-config-demo");
  }


}
