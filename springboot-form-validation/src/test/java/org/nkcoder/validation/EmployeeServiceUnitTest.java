package org.nkcoder.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nkcoder.validation.model.Employee;
import org.nkcoder.validation.model.EmployeeId;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceUnitTest {

  private EmployeeService employeeService = new EmployeeService();

  @Test
  public void shouldCreateEmployee() {
    Employee employee = new Employee("dity", "LA", "4716489518654704", "05/20", "023");

    EmployeeId employeeId = employeeService.createEmployee(employee);

    assertThat(employeeId).isNotNull();
    assertThat(employeeId.getId()).hasSize(UUID.randomUUID().toString().length());
  }
}
