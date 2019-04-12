package org.nkcoder.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.nkcoder.domain.Employee;
import org.nkcoder.exception.EmployeeNotFoundException;
import org.nkcoder.repo.EmployeeRepository;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

  @Mock
  private EmployeeRepository employeeRepository;

  private EmployeeService employeeService;

  @Before
  public void setUp() {
    employeeService = new EmployeeService(employeeRepository);
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

  @Test(expected = EmployeeNotFoundException.class)
  public void getEmployee_notFound() {
    given(employeeRepository.findByName(anyString())).willReturn(null);

    employeeService.getEmployee("notExist");

  }

}
