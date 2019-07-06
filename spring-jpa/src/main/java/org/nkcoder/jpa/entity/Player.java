package org.nkcoder.jpa.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Table(name = "player")    // default to entity name
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)  // JPA need a default constructor
@RequiredArgsConstructor
@ToString
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private final Long id;

  private final String name;

  private final String team;

  private final LocalDateTime bornAt;

}
