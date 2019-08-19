package org.nkcoder.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.nkcoder.jpa.repository.PlaySpecification.bornAtYearsAgo;
import static org.nkcoder.jpa.repository.PlaySpecification.teamEquals;
import static org.springframework.data.jpa.domain.Specification.where;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.nkcoder.jpa.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

@DataJpaTest
@Disabled
public class PlayerRepositoryTest {

  @Autowired
  private PlayerRepository playerRepository;

  private Integer id;
  private String name;
  private DateTimeFormatter formatter;

  @BeforeEach
  public void setup() {
    id = 1;
    name = "Kobe";
    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  }


  @Test
  public void shouldReturnPlayer_when_findById() {
    Optional<Player> playerOpt = playerRepository.findById(id);

    assertThat(playerOpt).isNotNull();
    assertThat(playerOpt.isPresent()).isTrue();
    assertThat(playerOpt.get().getId()).isEqualTo(id);
  }

  @Test
  public void shouldReturnPlayer_when_findPlayerById() {
    Player player = playerRepository.findPlayerById(id);

    assertThat(player).isNotNull();
    assertThat(player.getId()).isEqualTo(id);
  }

  @Test
  public void shouldReturnPlayer_when_findByIdAndName() {
    Player player = playerRepository.findByIdAndName(id, name);

    assertThat(player).isNotNull();
    assertThat(player.getId()).isEqualTo(id);
    assertThat(player.getName()).isEqualTo(name);
  }

  @Test
  public void shouldReturnPlayer_when_readByIdAndCreateAtBetween() {
    Player player = playerRepository.readByIdAndJoinAtBetween(
        id,
        LocalDateTime.parse("2019-06-24 10:01:01", formatter),
        LocalDateTime.parse("2019-06-25 10:01:01", formatter)
    );

    assertThat(player).isNotNull();
    assertThat(player.getId()).isEqualTo(id);
    assertThat(player.getName()).isEqualTo(name);
  }

  @Test
  public void shouldReturnPlayer_when_getByCreateAtAfter() {
    List<Player> players = playerRepository.getByJoinAtAfter(
        LocalDateTime.parse("2019-06-24 10:01:01", formatter)
    );

    assertThat(players).isNotEmpty();
  }

  @Test
  public void shouldReturnPlayer_when_findByNameParam() {
    Player player = playerRepository.findByNameParam("Durant");

    assertThat(player).isNotNull();
    assertThat(player.getName()).isEqualTo("Durant");
  }

  @Test
  public void shouldReturnPlayer_when_findByName() {
    Player player = playerRepository.findByNameNativeQuery("Durant");

    assertThat(player).isNotNull();
    assertThat(player.getName()).isEqualTo("Durant");
  }

  @Test
  public void shouldReturnPlayer_when_findByTeamWithPage() {
    Page<Player> playerPage = playerRepository.findByTeamId("LA", PageRequest.of(0, 2));

    assertThat(playerPage).isNotEmpty();
    assertThat(playerPage.getTotalElements()).isEqualTo(5);
    assertThat(playerPage.getContent()).hasSize(2);
  }

  @Test
  public void shouldUpdatePlayer() {
    playerRepository.updateTeamIdByName("Lebron", "LA 2");
    Player james = playerRepository.findByName("Lebron");
    assertThat(james).isNotNull();
  }

  @Test
  public void shouldReturnPlays_whenFindByTeam() {
    List<Player> players = playerRepository.findByTeamIdOrderByJoinAtDesc("LA");
    assertThat(players).isNotEmpty();

    List<Player> playersOfPageOneDesc = playerRepository
        .findByTeamIdAndPage("LA", PageRequest.of(0, 3, Sort.by(Order.desc("bornAt"))));
    assertThat(playersOfPageOneDesc).isNotEmpty();

    playerRepository.findAll(bornAtYearsAgo(1));
    playerRepository.findAll(teamEquals("LA"));
    playerRepository.findAll(where(bornAtYearsAgo(1)).and(teamEquals("LA")));

  }


}

