package org.nkcoder.quotation.repository;

import org.nkcoder.quotation.domain.model.HomePolicyQuotation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HomePolicyQuotationRepository extends CrudRepository<HomePolicyQuotation, Long> {
    boolean existsByQuoteId(String QuoteId);
}
