package org.nkcoder.validation.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class EmployeeId implements Serializable {

  private static final long serialVersionUID = 1538376060573011016L;

  private final String id;

  @JsonCreator
  public EmployeeId(@JsonProperty("id") String id) {
    this.id = id;
  }
}
