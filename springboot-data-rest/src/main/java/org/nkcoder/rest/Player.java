package org.nkcoder.rest;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@Getter
@RestResource(rel = "players", path = "players")
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  private final String name;
  private final Integer teamId;
  private final LocalDate joinAt;

}
