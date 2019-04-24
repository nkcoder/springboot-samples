package org.nkcoder.quotation.domain.model;

import org.nkcoder.common.Gender;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "CAR_POLICY_QUOTATION")
public class CarPolicyQuotation extends Quotation {
    private String productiveYear;
    private String carBrand;
    private String carModel;
    private String parkPlace;
    private String distancePreYear;
    private LocalDate driverBirthDay;
    @Enumerated(EnumType.STRING)
    private Gender driverGender;

    public String getProductiveYear() {
        return productiveYear;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getParkPlace() {
        return parkPlace;
    }

    public String getDistancePreYear() {
        return distancePreYear;
    }

    public LocalDate getDriverBirthDay() {
        return driverBirthDay;
    }

    public Gender getDriverGender() {
        return driverGender;
    }
}
