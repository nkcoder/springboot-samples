package org.nkcoder.validation;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nkcoder.validation.controller.EmployeeController;
import org.nkcoder.validation.exception.GlobalExceptionHandler;
import org.nkcoder.validation.model.Employee;
import org.nkcoder.validation.model.EmployeeId;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerUnitTest {

  @Mock private EmployeeService employeeService;
  @InjectMocks private EmployeeController employeeController;
  @InjectMocks private GlobalExceptionHandler globalExceptionHandler;

  @BeforeEach
  void setup() {
    RestAssuredMockMvc.standaloneSetup(employeeController, globalExceptionHandler);
  }

  @Test
  public void shouldCreateEmployee() {
    Employee employee = new Employee("dity", "LA", "4716489518654704", "05/20", "023");
    EmployeeId id = new EmployeeId(UUID.randomUUID().toString());
    when(employeeService.createEmployee(ArgumentMatchers.any(Employee.class))).thenReturn(id);

    given()
        .contentType(ContentType.JSON)
        .body(employee)
        .when()
        .post("/employees")
        .then()
        .status(HttpStatus.CREATED)
        .contentType(ContentType.JSON)
        .body("id", equalTo(id.getId()));
  }

  @Test
  public void shouldReturnErrorMessageWhenCreditCardInvalid() {

    Employee employee = new Employee("dity", "LA", "not-a-number", "05/20", "023");

    given()
        .contentType(ContentType.JSON)
        .body(employee)
        .when()
        .post("/employees")
        .then()
        .status(HttpStatus.BAD_REQUEST)
        .contentType(ContentType.JSON)
        .body("message", containsString("invalid credit card number"));
  }
}
