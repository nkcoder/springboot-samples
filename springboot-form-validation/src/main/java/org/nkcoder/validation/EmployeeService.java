package org.nkcoder.validation;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.nkcoder.validation.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

  private final ConcurrentHashMap<String, Employee> employees = new ConcurrentHashMap<>();

  public String createEmployee(Employee employee) {
    String id = UUID.randomUUID().toString();
    employees.putIfAbsent(id, employee);
    return id;
  }

}
