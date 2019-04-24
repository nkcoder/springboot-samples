package org.nkcoder.policy.repository;

import org.nkcoder.policy.domain.model.HomePolicy;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface HomePolicyRepository extends BasePolicyRepository<HomePolicy> {
}
