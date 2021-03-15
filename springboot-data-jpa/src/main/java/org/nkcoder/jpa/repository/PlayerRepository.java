package org.nkcoder.jpa.repository;

import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
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
   * 0. default queries inherited from JpaRepository
   */

  /**
   * 1. basic query
   */
  Player findPlayerById(Integer id);

  Player findByIdAndName(Integer id, String name);

  Player readByIdAndJoinAtBetween(Integer id, LocalDate from, LocalDate to);

  List<Player> getByJoinAtAfter(LocalDate from);

  Iterable<Player> getByJoinAtBefore(LocalDate to);

  /**
   * 2. page and sort
   */
  Page<Player> findByJoinAtAfter(LocalDate from, Pageable pageable);

  List<Player> findByTeam(String team, Sort sort);

  /**
   * 3. @Query
   */
  @Query(value = "SELECT p FROM Player p where p.name = :name")
  Player findByNameNamedParam(@Param("name") String name);

  @Query(value = "SELECT p FROM Player p where p.name = ?1")
  Player findByNamePositionParam(String name);

  @Query("SELECT p FROM Player p")
  Page<Player> findAllAndPage(Pageable pageable);

  @Query(value = "SELECT id, name, team, join_at FROM player where name = ?1", nativeQuery = true)
  Player findByNameNativeQuery(String name);

  @Query(value = "UPDATE player SET name = :name WHERE id = :id", nativeQuery = true)
  @Modifying
  @Transactional
  int updateNameById(@Param("id") Integer id, @Param("name") String name);

  /**
   * 4. specification and QSL
   *
   * @link: PlayerSpecification
   */


}
