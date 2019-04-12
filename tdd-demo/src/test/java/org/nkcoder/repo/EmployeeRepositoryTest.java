package org.nkcoder.repo;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nkcoder.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EmployeeRepositoryTest {

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void getEmployee_returnEmployeeInfo() {
    Employee employeeSaved = entityManager.persistAndFlush(new Employee("daniel", "junior"));

    Employee employee = employeeRepository.findByName("daniel");

    assertThat(employee).isNotNull();
    assertThat(employee.getName()).isEqualTo(employeeSaved.getName());
    assertThat(employee.getGrade()).isEqualTo(employeeSaved.getGrade());

  }

}