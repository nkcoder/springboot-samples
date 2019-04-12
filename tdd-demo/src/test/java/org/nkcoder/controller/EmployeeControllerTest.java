package org.nkcoder.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.nkcoder.domain.Employee;
import org.nkcoder.exception.EmployeeNotFoundException;
import org.nkcoder.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
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

}
