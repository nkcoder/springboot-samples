package org.nkcoder.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nkcoder.domain.Employee;
import org.nkcoder.exception.EmployeeNotFoundException;
import org.nkcoder.repo.EmployeeRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

  @MockBean
  private EmployeeRepository employeeRepository;

  private EmployeeService employeeService;

  @BeforeEach
  public void setUp() {
    employeeService = new EmployeeServiceImpl(employeeRepository);
  }

  @Test
  public void getEmployee_returnEmployeeInfo() {
    String name = "daniel";
    given(employeeRepository.findByName(name)).willReturn(new Employee(name, "junior"));

    Employee employee = employeeService.getEmployee(name);

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(employee).isNotNull();
    softAssertions.assertThat(employee.getName()).isEqualTo(name);
    softAssertions.assertThat(employee.getGrade()).isEqualTo("junior");
    softAssertions.assertAll();

  }

  @Test
  public void getEmployee_notFound() {
    given(employeeRepository.findByName(anyString())).willReturn(null);

    assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployee("notExist"));

  }

  @Test
  public void saveEmployee_returnSavedEmployee() {
    Employee employee = new Employee("daniel", "senior");
    when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

    Employee employeeSaved = employeeService.saveEmployee(employee);

    assertThat(employeeSaved).isNotNull();
    assertThat(employeeSaved.getName()).isEqualTo(employee.getName());
    assertThat(employeeSaved.getGrade()).isEqualTo(employee.getGrade());

  }
}
