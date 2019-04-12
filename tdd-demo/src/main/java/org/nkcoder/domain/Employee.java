package org.nkcoder.domain;

public class Employee {

  private String name;
  private String grade;

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
}
