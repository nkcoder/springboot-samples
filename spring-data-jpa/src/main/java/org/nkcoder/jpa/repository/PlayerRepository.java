package org.nkcoder.jpa.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.nkcoder.jpa.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer>,
    JpaSpecificationExecutor<Player> {

  /**
   * find one
   */
  Player findPlayerById(Integer id);

  Player findByIdAndName(Integer id, String name);

  Player readByIdAndJoinAtBetween(Integer id, LocalDateTime from, LocalDateTime to);

  /**
   * find list by page or sort
   */
  List<Player> getByJoinAtAfter(LocalDateTime from);

  Iterable<Player> getByJoinAtBefore(LocalDateTime to);

  List<Player> findByTeamIdOrderByJoinAtDesc(String teamId);

  Page<Player> findByTeamId(String teamId, Pageable pageable);

  List<Player> findByTeamId(String teamId, Sort sort);

  /**
   * find by query
   */
  @Query(value = "SELECT p FROM Player p where p.name = :name")
  Player findByNameParam(@Param("name") String name);

  @Query(value = "SELECT p FROM Player p where p.name = ?1")
  Player findByName(String name);

  @Query(value = "SELECT p FROM Player p where p.teamId = ?1")
  List<Player> findByTeamIdAndPage(String teamId, Pageable pageable);

  @Query(value = "SELECT id, name, team, join_at FROM player where name = ?1", nativeQuery = true)
  Player findByNameNativeQuery(String name);

  @Query(value = "UPDATE player SET teamId = ?2 WHERE name = ?1", nativeQuery = true)
  @Modifying
  void updateTeamIdByName(String name, String team);

}
