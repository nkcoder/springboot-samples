package org.nkcoder.policy.domain.model;

import java.time.LocalDate;
import javax.persistence.Embeddable;
import lombok.Setter;

@Setter
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
