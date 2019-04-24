package org.nkcoder.quotation.domain.model;

import lombok.Getter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@MappedSuperclass
public abstract class Quotation {
    @Id
    private String quoteId = UUID.randomUUID().toString();
    private Double premium;

    public void setPremium(Double premium) {
        this.premium = premium;
    }
}
