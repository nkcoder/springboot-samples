package org.nkcoder.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nkcoder.domain.Employee;
import org.nkcoder.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;

  @Test
  public void getEmployee_shouldReturnEmployee() throws Exception {
    given(employeeService.getEmployee(anyString())).willReturn(new Employee("daniel", "junior"));

    mockMvc.perform(get("/employees/daniel"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value("daniel"))
        .andExpect(jsonPath("grade").value("junior"));
  }

  @Test
  public void getEmployee_notFound() throws Exception {
    given(employeeService.getEmployee(anyString())).willReturn(null);

    mockMvc.perform(get("/employees/notexist"))
        .andExpect(status().isNotFound());

  }

  @Test
  public void saveEmployee_returnEmployee() throws Exception {
    Employee employee = new Employee("kobe", "chief");
    given(employeeService.saveEmployee(any(Employee.class))).willReturn(employee);

    mockMvc.perform(
        post("/employees")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(toJson(employee))
            .accept(MediaType.APPLICATION_PROBLEM_JSON_UTF8)
    )
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value("kobe"))
        .andExpect(jsonPath("grade").value("chief"));

  }

  private String toJson(Object o) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(o);
  }

}
