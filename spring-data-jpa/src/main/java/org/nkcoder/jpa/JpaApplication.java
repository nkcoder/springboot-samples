package org.nkcoder.jpa;

import com.google.common.collect.Lists;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.nkcoder.jpa.entity.Player;
import org.nkcoder.jpa.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
public class JpaApplication {

  private final PlayerRepository playerRepository;

  public JpaApplication(PlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(JpaApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner() {
    return args -> {
      List<Player> players = Lists.newArrayList(
          new Player("p1", "t1", LocalDateTime.now()),
          new Player("p2", "t2", LocalDateTime.now()),
          new Player("p3", "t3", LocalDateTime.now())
      );
      playerRepository.saveAll(players);

      playerRepository.findAll().forEach(p -> log.info("player: {}", p.getId()));
    };
  }

}
