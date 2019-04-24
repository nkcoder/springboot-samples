package org.nkcoder.policy.repository;

import org.nkcoder.policy.domain.model.Policy;

import javax.transaction.Transactional;

@Transactional
public interface PolicyRepository extends BasePolicyRepository<Policy> {
}
