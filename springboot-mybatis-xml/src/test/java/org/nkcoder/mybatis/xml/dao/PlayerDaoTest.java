package org.nkcoder.mybatis.xml.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.nkcoder.mybatis.xml.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@MybatisTest
@Import(PlayerDao.class)
public class PlayerDaoTest {

  @Autowired
  private PlayerDao playerDao;

  @Test
  public void shouldInsertPlayer() {
    Player player = new Player("dfa", "LA", LocalDate.now());
    int rowInserted = playerDao.insert(player);

    assertThat(rowInserted).isEqualTo(1);
    assertThat(player.getId()).isGreaterThanOrEqualTo(1);
  }

  @Test
  public void shouldDeletePlayerById() {
    Player player = new Player("dfa", "LA", LocalDate.now());
    playerDao.insert(player);

    int rowDeleted = playerDao.delete(player.getId());
    assertThat(rowDeleted).isEqualTo(1);
  }

  @Test
  public void shouldFindById() {
    Player player = new Player("dfa", "LA111", LocalDate.now());
    playerDao.insert(player);

    Player playerFound = playerDao.findById(player.getId());

    assertThat(playerFound).isNotNull();
    assertThat(playerFound.getName()).isEqualTo(player.getName());

    playerDao.findById(1);

    System.out.println("---");

  }

  @Test
  public void shouldUpdatePlayer() {
    Player player = new Player("dfa", "LA", LocalDate.now());
    playerDao.insert(player);

    Player newPlayer = new Player("kobe", "LA", LocalDate.now());
    newPlayer.setId(player.getId());
    playerDao.update(newPlayer);

    Player playerFound = playerDao.findById(player.getId());

    assertThat(playerFound).isNotNull();
    assertThat(playerFound.getName()).isEqualTo(newPlayer.getName());

  }
}
