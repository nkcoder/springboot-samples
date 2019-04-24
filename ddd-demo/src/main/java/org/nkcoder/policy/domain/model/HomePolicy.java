package org.nkcoder.policy.domain.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "HOME_POLICY")
public class HomePolicy extends Policy {

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
