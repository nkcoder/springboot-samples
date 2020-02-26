package org.nkcoder.validation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nkcoder.validation.model.Employee;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeIntegrationTest3 {

  @LocalServerPort
  private int port;

  @BeforeEach
  public void setup() {
    RestAssured.port = port;
  }

  @Test
  public void shouldCreateEmployee() {

    Employee employee = new Employee(
        "dity",
        "LA",
        "4716489518654704",
        "05/20",
        "023"
    );

    given().contentType("application/json").body(employee).
        when().post("/api/employees").then().statusCode(HttpStatus.CREATED.value());
  }

  @Test
  public void shouldReturnBadRequestWhenParamInvalid() {
    Employee employee = new Employee(
        "dity",
        "LA",
        "4716489518654704",
        "05",
        "023"
    );

    given().contentType("application/json").body(employee)
        .when().post("/api/employees").then().statusCode(HttpStatus.BAD_REQUEST.value())
        .body("message", equalTo("required format MM/YY"));


  }

}
