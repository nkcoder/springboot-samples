package org.nkcoder.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Table(name = "student")    // default to entity name
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)  // JPA need a default constructor
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;

  private String grade;

  private LocalDateTime createAt;

  @JsonCreator
  public Student(
      @JsonProperty("id") Long id,
      @JsonProperty("name") String name,
      @JsonProperty("grade") String grade,
      @JsonProperty("createAt") LocalDateTime createAt) {
    this.id = id;
    this.name = name;
    this.grade = grade;
    this.createAt = createAt;
  }


}
