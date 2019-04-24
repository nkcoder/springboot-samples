package org.nkcoder.policy.domain.service;

import org.nkcoder.policy.command.CreateCarPolicyCommand;
import org.nkcoder.policy.command.CreateHomePolicyCommand;
import org.nkcoder.policy.domain.model.CarPolicy;
import org.nkcoder.policy.domain.model.HomePolicy;
import org.nkcoder.policy.mapper.CarPolicyMapper;
import org.nkcoder.policy.mapper.HomePolicyMapper;
import org.nkcoder.policy.repository.CarPolicyRepository;
import org.nkcoder.policy.repository.HomePolicyRepository;
import org.nkcoder.quotation.exception.InvalidQuotationException;
import org.nkcoder.quotation.repository.CarPolicyQuotationRepository;
import org.nkcoder.quotation.repository.HomePolicyQuotationRepository;
import org.springframework.stereotype.Service;

@Service
public class PolicyFactoryService {

    private HomePolicyRepository homePolicyRepository;
    private HomePolicyQuotationRepository homePolicyQuotationRepository;
    private CarPolicyRepository carPolicyRepository;
    private CarPolicyQuotationRepository carPolicyQuotationRepository;

    private CarPolicyMapper carPolicyMapper = new CarPolicyMapper();
    private HomePolicyMapper homePolicyMapper = new HomePolicyMapper();

    public PolicyFactoryService(HomePolicyRepository homePolicyRepository,
                                HomePolicyQuotationRepository homePolicyQuotationRepository,
                                CarPolicyRepository carPolicyRepository,
                                CarPolicyQuotationRepository carPolicyQuotationRepository) {
        this.homePolicyRepository = homePolicyRepository;
        this.homePolicyQuotationRepository = homePolicyQuotationRepository;
        this.carPolicyRepository = carPolicyRepository;
        this.carPolicyQuotationRepository = carPolicyQuotationRepository;
    }

    public HomePolicy createPolicy(CreateHomePolicyCommand command) {
        HomePolicy homePolicy = homePolicyMapper.map(command, HomePolicy.class);
        if (!homePolicyQuotationRepository.existsByQuoteId(homePolicy.getQuoteId())) {
            throw new InvalidQuotationException();
        }

        homePolicyRepository.save(homePolicy);
        return homePolicy;
    }

    public CarPolicy createPolicy(CreateCarPolicyCommand command) {
        CarPolicy carPolicy = carPolicyMapper.map(command, CarPolicy.class);
        if (!carPolicyQuotationRepository.existsByQuoteId(carPolicy.getQuoteId())) {
            throw new InvalidQuotationException();
        }

        carPolicyRepository.save(carPolicy);
        return carPolicy;
    }
}
