package org.nkcoder.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Order implements Serializable {

  private final String id;
  private final String name;

  @JsonCreator
  public Order(@JsonProperty("id") String id, @JsonProperty("name") String name) {
    this.id = id;
    this.name = name;
  }
}
