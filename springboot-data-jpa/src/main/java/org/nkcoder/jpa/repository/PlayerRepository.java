package org.nkcoder.jpa.repository;

import java.time.LocalDate;
import java.util.List;
import org.nkcoder.jpa.entity.PlayerEntity;
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
public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer>,
    JpaSpecificationExecutor<PlayerEntity> {

  /**
   * 0. default queries inherited from JpaRepository
   */

  /**
   * 1. basic query
   */
  PlayerEntity findPlayerById(Integer id);

  PlayerEntity findByIdAndName(Integer id, String name);

  PlayerEntity readByIdAndJoinAtBetween(Integer id, LocalDate from, LocalDate to);

  List<PlayerEntity> getByJoinAtAfter(LocalDate from);

  Iterable<PlayerEntity> getByJoinAtBefore(LocalDate to);

  /**
   * 2. page and sort
   */
  Page<PlayerEntity> findByJoinAtAfter(LocalDate from, Pageable pageable);

  List<PlayerEntity> findByTeam(String team, Sort sort);

  /**
   * 3. @Query
   */
  @Query(value = "SELECT p FROM PlayerEntity p where p.name = :name")
  PlayerEntity findByNameNamedParam(@Param("name") String name);

  @Query(value = "SELECT p FROM PlayerEntity p where p.name = ?1")
  PlayerEntity findByNamePositionParam(String name);

  @Query("SELECT p FROM PlayerEntity p")
  Page<PlayerEntity> findAllAndPage(Pageable pageable);

  @Query(value = "SELECT id, name, team, join_at FROM player where name = ?1", nativeQuery = true)
  PlayerEntity findByNameNativeQuery(String name);

  @Query(value = "UPDATE player SET name = :name WHERE id = :id", nativeQuery = true)
  @Modifying
  int updateNameById(@Param("id") Integer id, @Param("name") String name);

  /**
   * 4. specification and QSL
   *
   * @link: PlayerSpecification
   */


}
