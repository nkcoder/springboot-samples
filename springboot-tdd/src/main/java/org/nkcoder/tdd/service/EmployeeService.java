package org.nkcoder.tdd.service;

import org.nkcoder.tdd.domain.Employee;

public interface EmployeeService {

    Employee getEmployee(String name);

    Employee saveEmployee(Employee employee);
}
