package org.nkcoder.quotation.domain.model;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class Quotation {
    @Id
    private String quoteId = UUID.randomUUID().toString();
    private Double premium;

    public String getQuoteId() {
        return quoteId;
    }

    public Double getPremium() {
        return premium;
    }
}
