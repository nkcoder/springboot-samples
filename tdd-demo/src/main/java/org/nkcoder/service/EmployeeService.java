package org.nkcoder.service;

import org.nkcoder.domain.Employee;

public interface EmployeeService {

  Employee getEmployee(String name);

  Employee saveEmployee(Employee employee);
}
