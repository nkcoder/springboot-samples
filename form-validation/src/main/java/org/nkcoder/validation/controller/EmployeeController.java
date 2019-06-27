package org.nkcoder.validation.controller;

import java.util.UUID;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.nkcoder.validation.model.Employee;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

  @PostMapping("")
  public String addEmploy(@RequestBody  @Valid Employee employee) {
    log.info("employee to create: {}", employee);

    String employeeId = UUID.randomUUID().toString();
    return employeeId;
  }

}
