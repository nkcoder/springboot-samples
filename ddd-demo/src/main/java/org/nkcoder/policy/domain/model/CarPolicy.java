package org.nkcoder.policy.domain.model;

import lombok.Getter;
import org.nkcoder.common.Gender;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
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
