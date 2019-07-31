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
public interface PlayerRepository extends JpaRepository<Player, Long>,
    JpaSpecificationExecutor<Player> {

  /**
   * find one
   */
  Player findPlayerById(Long id);

  Player findByIdAndName(Long id, String name);

  Player readByIdAndBornAtBetween(Long id, LocalDateTime from, LocalDateTime to);

  /**
   * find list by page or sort
   */
  List<Player> getByBornAtAfter(LocalDateTime from);

  Iterable<Player> getByBornAtBefore(LocalDateTime to);

  List<Player> findByTeamOrderByBornAtDesc(String team);

  Page<Player> findByTeam(String team, Pageable pageable);

  List<Player> findByTeam(String team, Sort sort);

  /**
   * find by query
   */
  @Query(value = "SELECT p FROM Player p where p.name = :name")
  Player findByNameParam(@Param("name") String name);

  @Query(value = "SELECT p FROM Player p where p.name = ?1")
  Player findByName(String name);

  @Query(value = "SELECT p FROM Player p where p.team = ?1")
  List<Player> findByTeamAndPage(String name, Pageable pageable);

  @Query(value = "SELECT id, name, team, born_at FROM player where name = ?1", nativeQuery = true)
  Player findByNameNativeQuery(String name);

  @Query(value = "UPDATE player SET team = ?2 WHERE name = ?1", nativeQuery = true)
  @Modifying
  void updateTeamByName(String name, String team);

}
