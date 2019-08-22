package org.nkcoder.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nkcoder.jpa.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class PlayerRepositoryTest {

  @Autowired
  private PlayerRepository playerRepository;

  @BeforeEach
  public void setup() {

  }


  @Test
  public void defaultQueriesTEst() {
    Optional<Player> playerOpt = playerRepository.findById(1);
    assertThat(playerOpt.isPresent()).isTrue();
    assertThat(playerOpt.get().getId()).isEqualTo(1);

    boolean exists = playerRepository.existsById(2);
    assertThat(exists).isTrue();
  }

  @Test
  public void basicQueriesTest() {
    Player player = playerRepository.findPlayerById(1);
    assertThat(player).isNotNull();
    assertThat(player.getId()).isEqualTo(1);

    Player kobe = playerRepository.findByIdAndName(1, "Kobe");
    assertThat(kobe).isNotNull();
    assertThat(kobe.getName()).isEqualTo("Kobe");

    Player aPlayer = playerRepository
        .readByIdAndJoinAtBetween(1, LocalDate.parse("1995-01-01"), LocalDate.now());
    assertThat(aPlayer).isNotNull();

  }

//
//  @Test
//  public void shouldReturnPlays_whenFindByTeam() {
//    List<Player> players = playerRepository.findByTeamIdOrderByJoinAtDesc("LA");
//    assertThat(players).isNotEmpty();
//
//    List<Player> playersOfPageOneDesc = playerRepository
//        .findByTeamIdAndPage("LA", PageRequest.of(0, 3, Sort.by(Order.desc("bornAt"))));
//    assertThat(playersOfPageOneDesc).isNotEmpty();
//
//    playerRepository.findAll(bornAtYearsAgo(1));
//    playerRepository.findAll(teamEquals("LA"));
//    playerRepository.findAll(where(bornAtYearsAgo(1)).and(teamEquals("LA")));
//
//  }


}

