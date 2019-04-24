package org.nkcoder.policy.repository;

import javax.transaction.Transactional;
import org.nkcoder.policy.domain.model.Policy;

@Transactional
public interface PolicyRepository extends BasePolicyRepository<Policy> {
}
