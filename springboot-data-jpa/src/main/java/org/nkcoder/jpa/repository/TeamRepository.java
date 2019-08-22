package org.nkcoder.jpa.repository;

import org.nkcoder.jpa.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource
public interface TeamRepository extends JpaRepository<Team, Integer> {

}
