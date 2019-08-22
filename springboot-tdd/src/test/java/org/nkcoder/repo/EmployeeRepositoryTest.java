package org.nkcoder.repo;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nkcoder.tdd.domain.Employee;
import org.nkcoder.tdd.repo.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void getEmployee_returnEmployeeInfo() {
        Employee employeeSaved = entityManager.persistAndFlush(new Employee("daniel", "junior"));

        Employee employee = employeeRepository.findByName("daniel");

        assertThat(employee).isNotNull();
        assertThat(employee.getName()).isEqualTo(employeeSaved.getName());
        assertThat(employee.getGrade()).isEqualTo(employeeSaved.getGrade());

    }

    @Test
    public void getEmployee_whenNotFound_returnNull() {
        Employee notExist = employeeRepository.findByName("notExist");
        assertThat(notExist).isNull();
    }

    @Test
    public void saveEmployee_returnSavedEmployee() {
        Employee employee = new Employee("daniel", "principal");
        Employee employeeSaved = employeeRepository.save(employee);

        entityManager.flush();

        assertThat(employeeSaved).isNotNull();
        assertThat(employeeSaved.getName()).isEqualTo(employee.getName());
        assertThat(employeeSaved.getGrade()).isEqualTo(employee.getGrade());

    }

}