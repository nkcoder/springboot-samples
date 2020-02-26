package org.nkcoder.validation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nkcoder.validation.model.Employee;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

  private EmployeeService employeeService = new EmployeeService();

  @Test
  public void shouldCreateEmployee() {
    Employee employee = new Employee(
        "dity",
        "LA",
        "4716489518654704",
        "05/20",
        "023"
    );

    String employeeCreated = employeeService.createEmployee(employee);
    assertThat(employeeCreated).isNotEmpty();
  }

}
