package org.nkcoder.jpa.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.nkcoder.jpa.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface PlayerRepository extends JpaRepository<Player, Integer>,
    JpaSpecificationExecutor<Player> {

  /**
   * 0. default queries inherited from JpaRepository
   */

  /**
   * 1. basic query
   */
  Player findPlayerById(Integer id);

  Player findByIdAndName(Integer id, String name);

  Player readByIdAndJoinAtBetween(Integer id, LocalDate from, LocalDate to);

  /**
   * 2. page and sort
   */
  List<Player> getByJoinAtAfter(LocalDateTime from);

  Iterable<Player> getByJoinAtBefore(LocalDateTime to);

  /**
   * 3. @Query
   */
  @Query(value = "SELECT p FROM Player p where p.name = :name")
  Player findByNameParam(@Param("name") String name);

  @Query(value = "SELECT p FROM Player p where p.name = ?1")
  Player findByName(String name);

  @Query(value = "SELECT id, name, team, join_at FROM player where name = ?1", nativeQuery = true)
  Player findByNameNativeQuery(String name);

}
