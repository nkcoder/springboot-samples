package org.nkcoder.validation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nkcoder.validation.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EmployeeIntegrationTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  public void shouldCreateEmployee() {

    Employee employee = new Employee(
        "dity",
        "LA",
        "4716489518654704",
        "05/20",
        "023"
    );

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.put(HttpHeaders.CONTENT_TYPE, List.of(MediaType.APPLICATION_JSON_VALUE));
    HttpEntity httpEntity = new HttpEntity(employee, httpHeaders);

    ResponseEntity<String> responseEntity = testRestTemplate
        .postForEntity("/employees", httpEntity, String.class);
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
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

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.put(HttpHeaders.CONTENT_TYPE, List.of(MediaType.APPLICATION_JSON_VALUE));
    HttpEntity httpEntity = new HttpEntity(employee, httpHeaders);

    ResponseEntity<String> responseEntity = testRestTemplate
        .postForEntity("/employees", httpEntity, String.class);
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

  }

}
