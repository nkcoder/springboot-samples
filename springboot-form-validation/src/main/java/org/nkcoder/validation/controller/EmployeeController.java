package org.nkcoder.validation.controller;

import javax.validation.Valid;
import org.nkcoder.validation.EmployeeService;
import org.nkcoder.validation.model.Employee;
import org.nkcoder.validation.model.EmployeeId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public EmployeeId create(@RequestBody @Valid Employee employee) {
    return employeeService.createEmployee(employee);
  }
}
