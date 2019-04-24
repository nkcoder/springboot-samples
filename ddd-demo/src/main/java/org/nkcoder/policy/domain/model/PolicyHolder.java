package org.nkcoder.policy.domain.model;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class PolicyHolder {

    private String id;
    private String name;
    private String email;
    private LocalDate birthDay;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }
}
