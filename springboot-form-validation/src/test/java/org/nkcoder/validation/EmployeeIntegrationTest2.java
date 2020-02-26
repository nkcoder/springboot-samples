package org.nkcoder.validation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nkcoder.validation.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeIntegrationTest2 {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void shouldCreateEmployee() throws Exception {

    Employee employee = new Employee(
        "dity",
        "LA",
        "4716489518654704",
        "05/20",
        "023"
    );

    mockMvc
        .perform(post("/employees").content(objectMapper.writeValueAsString(employee)).contentType(
            MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
  }

  @Test
  public void shouldReturnBadRequestWhenParamInvalid() throws Exception {
    Employee employee = new Employee(
        "dity",
        "LA",
        "4716489518654704",
        "05",
        "023"
    );

    mockMvc.perform(post("/employees").content(objectMapper.writeValueAsString(employee))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message").value("required format MM/YY"));
  }

}
