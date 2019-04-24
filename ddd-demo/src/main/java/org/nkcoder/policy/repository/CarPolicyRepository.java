package org.nkcoder.policy.repository;

import org.nkcoder.policy.domain.model.CarPolicy;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CarPolicyRepository extends BasePolicyRepository<CarPolicy> {
}
