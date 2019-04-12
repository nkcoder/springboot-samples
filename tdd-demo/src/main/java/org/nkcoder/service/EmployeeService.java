package org.nkcoder.service;

import org.nkcoder.domain.Employee;
import org.nkcoder.exception.EmployeeNotFoundException;
import org.nkcoder.repo.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  private EmployeeRepository employeeRepository;

  public EmployeeService(EmployeeRepository employeeRepository) {

    this.employeeRepository = employeeRepository;
  }

  public Employee getEmployee(String name) {
    Employee employee = employeeRepository.findByName(name);

    if (employee == null) {
      throw new EmployeeNotFoundException();
    }

    return employee;
  }

}
