package org.nkcoder.quotation.domain.service;

import org.nkcoder.quotation.command.EnquiryCarPolicyCommand;
import org.nkcoder.quotation.command.EnquiryHomePolicyCommand;
import org.nkcoder.quotation.domain.model.CarPolicyQuotation;
import org.nkcoder.quotation.domain.model.HomePolicyQuotation;
import org.nkcoder.quotation.mapper.CarPolicyQuotationMapper;
import org.nkcoder.quotation.mapper.HomePolicyQuotationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {

    private HomePolicyQuotationMapper homePolicyQuotationMapper = new HomePolicyQuotationMapper();
    private CarPolicyQuotationMapper carPolicyQuotationMapper = new CarPolicyQuotationMapper();

    @Autowired
    private QuoteCalculator quoteCalculator;

    public HomePolicyQuotation createQuotation(EnquiryHomePolicyCommand command) {
        HomePolicyQuotation homePolicyQuotation = homePolicyQuotationMapper.map(command, HomePolicyQuotation.class);
        homePolicyQuotation.setPremium(quoteCalculator.calculate(homePolicyQuotation));
        return homePolicyQuotation;
    }

    public CarPolicyQuotation createQuotation(EnquiryCarPolicyCommand command) {
        CarPolicyQuotation carPolicyQuotation = carPolicyQuotationMapper.map(command, CarPolicyQuotation.class);
        carPolicyQuotation.setPremium(quoteCalculator.calculate(carPolicyQuotation));
        return carPolicyQuotation;
    }
}
