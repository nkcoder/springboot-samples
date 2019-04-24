package org.nkcoder.controller;

import org.nkcoder.domain.Employee;
import org.nkcoder.exception.EmployeeNotFoundException;
import org.nkcoder.service.EmployeeServiceImpl;
import org.springframework.web.bind.annotation.*;

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
