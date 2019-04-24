package org.nkcoder.service;

import org.nkcoder.domain.Employee;
import org.nkcoder.exception.EmployeeNotFoundException;
import org.nkcoder.repo.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee getEmployee(String name) {
        Employee employee = employeeRepository.findByName(name);

        if (employee == null) {
            throw new EmployeeNotFoundException();
        }

        return employee;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

}
