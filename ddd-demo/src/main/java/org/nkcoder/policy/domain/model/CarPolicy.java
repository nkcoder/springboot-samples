package org.nkcoder.policy.domain.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.nkcoder.common.Gender;

@Getter
@Setter
@Entity
@Table(name = "CAR_POLICY")
public class CarPolicy extends Policy {

  private String productiveYear;
  private String carBrand;
  private String carModel;
  private String parkPlace;
  private String distancePreYear;
  private LocalDate driverBirthDay;
  @Enumerated(EnumType.STRING)
  private Gender driverGender;
}
