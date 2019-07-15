package org.nkcoder.rest;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(collectionResourceRel = "players", path = "players")
public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {

  List<Player> findByName(@Param("name") String name);

}
