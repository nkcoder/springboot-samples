package org.nkcoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nkcoder.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integration.properties")
public class IntegrationTest {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  public void saveEmployee_thenGetEmployee_shouldReturnEmployee() {
    Employee employee = new Employee("lebrown", "chief");

    ResponseEntity<Employee> postResponse = testRestTemplate.postForEntity(
        "/employees",
        employee,
        Employee.class
    );

    assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(postResponse.getBody()).isNotNull();
    assertThat(postResponse.getBody().getName()).isEqualTo(employee.getName());
    assertThat(postResponse.getBody().getGrade()).isEqualTo(employee.getGrade());

    //===================================================================

    ResponseEntity<Employee> getResponse = testRestTemplate.getForEntity(
        "/employees/{name}",
        Employee.class,
        employee.getName()
    );

    assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(getResponse.getBody()).isNotNull();
    assertThat(getResponse.getBody().getName()).isEqualTo(employee.getName());
    assertThat(getResponse.getBody().getGrade()).isEqualTo(employee.getGrade());

  }

}
