package org.nkcoder.policy.domain.model;

import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.Setter;

@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "POLICY")
public class Policy {
    @Id
    private String policyNumber = UUID.randomUUID().toString();
    private LocalDate startDate;
    private String quoteId;
    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "id", column = @Column(name = "policy_holder_id")),
            @AttributeOverride(name = "name", column = @Column(name = "policy_holder_name")),
            @AttributeOverride(name = "email", column = @Column(name = "policy_holder_email")),
            @AttributeOverride(name = "birthDay", column = @Column(name = "policy_holder_birthday"))
    })
    private PolicyHolder policyHolder;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getQuoteId() {
        return quoteId;
    }

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }
}
