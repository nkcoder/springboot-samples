package org.nkcoder.quotation;

import org.nkcoder.quotation.command.EnquiryCarPolicyCommand;
import org.nkcoder.quotation.command.EnquiryHomePolicyCommand;
import org.nkcoder.quotation.domain.model.CarPolicyQuotation;
import org.nkcoder.quotation.domain.model.HomePolicyQuotation;
import org.nkcoder.quotation.domain.service.QuoteService;
import org.nkcoder.quotation.repository.CarPolicyQuotationRepository;
import org.nkcoder.quotation.repository.HomePolicyQuotationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class QuotationApplicationService {

    private static Logger logger = LoggerFactory.getLogger(QuotationApplicationService.class);

    private QuoteService quoteService;
    private HomePolicyQuotationRepository homePolicyQuotationRepository;
    private CarPolicyQuotationRepository carPolicyQuotationRepository;

    public QuotationApplicationService(QuoteService quoteService,
                                       HomePolicyQuotationRepository homePolicyQuotationRepository,
                                       CarPolicyQuotationRepository carPolicyQuotationRepository) {
        this.quoteService = quoteService;
        this.homePolicyQuotationRepository = homePolicyQuotationRepository;
        this.carPolicyQuotationRepository = carPolicyQuotationRepository;
    }

    public HomePolicyQuotation calculateQuote(EnquiryHomePolicyCommand command) {
        HomePolicyQuotation homePolicyQuotation = quoteService.createQuotation(command);
        logger.info("Calculate home quote: [{}]", homePolicyQuotation.getQuoteId());
        homePolicyQuotationRepository.save(homePolicyQuotation);
        return homePolicyQuotation;
    }

    public CarPolicyQuotation calculateQuote(EnquiryCarPolicyCommand command) {
        CarPolicyQuotation carPolicyQuotation = quoteService.createQuotation(command);
        logger.info("Calculate car quote: [{}]", carPolicyQuotation.getQuoteId());
        carPolicyQuotationRepository.save(carPolicyQuotation);
        return carPolicyQuotation;
    }
}
