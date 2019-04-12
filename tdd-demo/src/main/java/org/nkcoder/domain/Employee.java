package org.nkcoder.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  private String grade;

  public Employee() {
  }

  public Employee(String name, String grade) {

    this.name = name;
    this.grade = grade;
  }

  public String getName() {
    return name;
  }

  public String getGrade() {
    return grade;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

}
