package org.nkcoder.validation;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.nkcoder.validation.model.Employee;
import org.nkcoder.validation.model.EmployeeId;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  private static final ConcurrentHashMap<String, Employee> EMPLOYEES = new ConcurrentHashMap<>();

  public EmployeeId createEmployee(Employee employee) {
    String id = UUID.randomUUID().toString();
    EMPLOYEES.putIfAbsent(id, employee);
    return new EmployeeId(id);
  }
}
