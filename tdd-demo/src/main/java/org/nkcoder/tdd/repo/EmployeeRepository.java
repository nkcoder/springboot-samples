package org.nkcoder.tdd.repo;

import org.nkcoder.tdd.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    Employee findByName(String name);

}
