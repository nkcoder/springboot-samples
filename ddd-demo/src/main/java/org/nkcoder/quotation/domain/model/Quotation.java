package org.nkcoder.quotation.domain.model;

import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
