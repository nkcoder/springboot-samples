package org.nkcoder.policy;

import org.nkcoder.policy.command.CreateCarPolicyCommand;
import org.nkcoder.policy.command.CreateHomePolicyCommand;
import org.nkcoder.policy.domain.model.CarPolicy;
import org.nkcoder.policy.domain.model.HomePolicy;
import org.nkcoder.policy.domain.service.PolicyFactoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PolicyApplicationService {

    private static Logger logger = LoggerFactory.getLogger(PolicyApplicationService.class);

    @Autowired
    private PolicyFactoryService policyFactoryService;

    public String createPolicy(CreateHomePolicyCommand command) {
        HomePolicy homePolicy = policyFactoryService.createPolicy(command);
        logger.info("Create home policy [{}] success", homePolicy.getPolicyNumber());
        return homePolicy.getPolicyNumber();
    }

    public String createPolicy(CreateCarPolicyCommand command) {
        CarPolicy carPolicy = policyFactoryService.createPolicy(command);
        logger.info("Create home policy [{}] success", carPolicy.getPolicyNumber());
        return carPolicy.getPolicyNumber();
    }
}
