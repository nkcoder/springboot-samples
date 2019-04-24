package org.nkcoder.quotation.domain.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "HOME_POLICY_QUOTATION")
public class HomePolicyQuotation extends Quotation {
    private String buildingMaterial;
    private String buildingType;
    private String numberOfRooms;

    public String getBuildingMaterial() {
        return buildingMaterial;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public String getNumberOfRooms() {
        return numberOfRooms;
    }

}
