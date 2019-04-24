package org.nkcoder.policy.mapper;

import org.nkcoder.common.BaseMapper;
import org.nkcoder.policy.command.CreateCarPolicyCommand;
import org.nkcoder.policy.domain.model.CarPolicy;

public class CarPolicyMapper extends BaseMapper {
    public CarPolicyMapper() {
        classMap(CreateCarPolicyCommand.class, CarPolicy.class)
                .field("holderName", "policyHolder.name")
                .field("holderEmail", "policyHolder.email")
                .field("holderId", "policyHolder.id")
                .field("holderBirthday", "policyHolder.birthDay")
                .byDefault()
                .register();
    }
}
