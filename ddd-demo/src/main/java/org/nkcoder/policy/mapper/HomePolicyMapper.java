package org.nkcoder.policy.mapper;

import org.nkcoder.common.BaseMapper;
import org.nkcoder.policy.command.CreateHomePolicyCommand;
import org.nkcoder.policy.domain.model.HomePolicy;

public class HomePolicyMapper extends BaseMapper {
    public HomePolicyMapper() {
        classMap(CreateHomePolicyCommand.class, HomePolicy.class)
                .field("holderName", "policyHolder.name")
                .field("holderEmail", "policyHolder.email")
                .field("holderId", "policyHolder.id")
                .field("holderBirthday", "policyHolder.birthDay")
                .byDefault()
                .register();
    }
}
