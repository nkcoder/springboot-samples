package org.nkcoder.quotation.repository;

import org.nkcoder.quotation.domain.model.CarPolicyQuotation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarPolicyQuotationRepository extends CrudRepository<CarPolicyQuotation, Long> {
    boolean existsByQuoteId(String quoteId);
}
