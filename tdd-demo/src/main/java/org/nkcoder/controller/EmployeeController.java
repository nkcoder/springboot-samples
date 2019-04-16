package org.nkcoder.controller;

import org.nkcoder.domain.Employee;
import org.nkcoder.exception.EmployeeNotFoundException;
import org.nkcoder.service.EmployeeServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

  private EmployeeServiceImpl employService;

  public EmployeeController(EmployeeServiceImpl employService) {
    this.employService = employService;
  }

  @GetMapping("/employees/{name}")
  public Employee getEmployee(@PathVariable("name") String name) {

    Employee employee = employService.getEmployee(name);
    if (employee == null) {
      throw new EmployeeNotFoundException();
    }

    return employee;
  }

  @PostMapping("/employees")
  public Employee saveEmployee(@RequestBody Employee employee) {
    return employService.saveEmployee(employee);
  }

}
