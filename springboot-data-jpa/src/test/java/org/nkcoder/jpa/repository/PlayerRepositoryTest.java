package org.nkcoder.jpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.jpa.domain.Specification.not;
import static org.springframework.data.jpa.domain.Specification.where;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nkcoder.jpa.entity.Player;
import org.nkcoder.jpa.specification.Operation;
import org.nkcoder.jpa.specification.PlayerSpecification;
import org.nkcoder.jpa.specification.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
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

    List<Player> byJoinAtAfter = playerRepository.getByJoinAtAfter(LocalDate.of(1974, 5, 1));
    assertThat(byJoinAtAfter).isNotEmpty();

    Iterable<Player> byJoinAtBefore = playerRepository.getByJoinAtBefore(LocalDate.of(1998, 1, 1));
    assertThat(byJoinAtBefore).isNotEmpty();
  }

  @Test
  public void pageAndSortTest() {
    Page<Player> byJoinAtAfter = playerRepository
        .findByJoinAtAfter(LocalDate.of(1995, 1, 1), PageRequest.of(0, 10));
    assertThat(byJoinAtAfter.getContent()).isNotEmpty();

    Page<Player> byJoinAtAfterAndSort = playerRepository
        .findByJoinAtAfter(LocalDate.of(1995, 1, 1),
            PageRequest.of(0, 10, Sort.by(Order.desc("joinAt"))));
    assertThat(byJoinAtAfterAndSort.getContent()).isNotEmpty();

    List<Player> byTeamIdAndSort = playerRepository.findByTeamId(1, Sort.by("joinAt"));
    assertThat(byTeamIdAndSort).isNotEmpty();
  }

  @Test
  public void queryTest() {
    Player oneal = playerRepository.findByNameNamedParam("Oneal");
    assertThat(oneal).isNotNull();

    Player oneal1 = playerRepository.findByNamePositionParam("Oneal");
    assertThat(oneal1).isNotNull();

    Page<Player> allAndPage = playerRepository.findAllAndPage(PageRequest.of(0, 5));
    assertThat(allAndPage.getContent().size()).isEqualTo(5);

    Player kobe = playerRepository.findByNameNativeQuery("Kobe");
    assertThat(kobe).isNotNull();

    int updated = playerRepository.updateNameById(1, "Bryant");
    assertThat(updated).isEqualTo(1);
  }

  @Test
  public void specificationTest() {
    PlayerSpecification spec1 = new PlayerSpecification(
        new SearchCriteria("joinAt", Operation.GREATER_THAN, LocalDate.parse("1995-05-01")));
    PlayerSpecification spec2 = new PlayerSpecification(
        new SearchCriteria("teamId", Operation.EQUAL, 1));

    List<Player> players1 = playerRepository.findAll(spec1.and(spec2));
    assertThat(players1).isNotEmpty();
    assertThat(players1.size()).isEqualTo(2);

    List<Player> players2 = playerRepository.findAll(where(spec1).or(spec2));
    assertThat(players2.size()).isEqualTo(6);

    List<Player> players3 = playerRepository.findAll(not(spec2));
    assertThat(players3.size()).isEqualTo(5);

  }


}

