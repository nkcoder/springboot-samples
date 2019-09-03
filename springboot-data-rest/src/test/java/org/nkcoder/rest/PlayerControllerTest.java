package org.nkcoder.rest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PlayerControllerTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  public void shouldReturnPlayersInTeam() {
    ResponseEntity<Resources> players = testRestTemplate
        .getForEntity("/api/players/by-team?teamId=1", Resources.class);

    assertThat(players.getStatusCode()).isEqualTo(HttpStatus.OK);

  }

}
