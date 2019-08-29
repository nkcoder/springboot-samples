package org.nkcoder.security.repository;

import org.nkcoder.security.entity.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(name = "auth.store", havingValue = "service")
public interface UserRepository extends JpaRepository<User, Long> {

  User findByUsername(String name);

}
