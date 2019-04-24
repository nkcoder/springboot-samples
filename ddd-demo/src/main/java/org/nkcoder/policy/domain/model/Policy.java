package org.nkcoder.policy.domain.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

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
