package org.nkcoder.mybatis.xml.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.nkcoder.mybatis.xml.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@MybatisTest
@AutoConfigureTestDatabase
public class PlayerMapperTest {

  @Autowired
  private PlayerMapper playerMapper;

  @Test
  public void shouldInsertPlayer() {
    Player player = new Player("dfa", "LA", LocalDate.now());
    int rowInserted = playerMapper.insert(player);

    assertThat(rowInserted).isEqualTo(1);
    assertThat(player.getId()).isGreaterThanOrEqualTo(1);
  }

  @Test
  public void shouldDeletePlayerById() {
    Player player = new Player("dfa", "LA", LocalDate.now());
    playerMapper.insert(player);

    int rowDeleted = playerMapper.delete(player.getId());
    assertThat(rowDeleted).isEqualTo(1);
  }

  @Test
  public void shouldFindById() {
    Player player = new Player("dfa", "LA111", LocalDate.now());
    playerMapper.insert(player);

    Player playerFound = playerMapper.findById(player.getId());

    assertThat(playerFound).isNotNull();
    assertThat(playerFound.getName()).isEqualTo(player.getName());

    playerMapper.findById(1);

    System.out.println("---");

  }

  @Test
  public void shouldUpdatePlayer() {
    Player player = new Player("dfa", "LA", LocalDate.now());
    playerMapper.insert(player);

    Player newPlayer = new Player("kobe", "LA", LocalDate.now());
    newPlayer.setId(player.getId());
    playerMapper.update(newPlayer);

    Player playerFound = playerMapper.findById(player.getId());

    assertThat(playerFound).isNotNull();
    assertThat(playerFound.getName()).isEqualTo(newPlayer.getName());

  }

}
