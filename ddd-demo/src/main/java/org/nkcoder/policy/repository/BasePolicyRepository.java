package org.nkcoder.policy.repository;

import java.util.Optional;
import org.nkcoder.policy.domain.model.Policy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface BasePolicyRepository<T extends Policy> extends CrudRepository<T, Long> {
    Optional<T> findByPolicyNumber(String PolicyNumber);
}
