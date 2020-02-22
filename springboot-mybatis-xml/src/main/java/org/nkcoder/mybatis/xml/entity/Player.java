package org.nkcoder.mybatis.xml.entity;

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class Player {

  @Setter
  private Integer id;

  private String name;

  private String team;

  private LocalDate joinAt;

  public Player(String name, String team, LocalDate joinAt) {
    this.name = name;
    this.team = team;
    this.joinAt = joinAt;
  }
}
