package com.github.pedrobacchini.springboottesting.repository;

import com.github.pedrobacchini.springboottesting.domain.Employee;
import com.github.pedrobacchini.springboottesting.util.EmployeeUtilTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        Employee alex = EmployeeUtilTest.getAlex();
        entityManager.persistAndFlush(alex);

        Employee found = employeeRepository.findByName(alex.getName());
        assertThat(found.getName()).isEqualTo(alex.getName());
    }

    @Test
    public void whenInvalidName_thenReturnNull() {
        Employee fromDb = employeeRepository.findByName("doesNotExist");
        assertThat(fromDb).isNull();
    }

    @Test
    public void whenFindById_thenReturnEmployee() {
        Employee bob = EmployeeUtilTest.getBob();
        entityManager.persistAndFlush(bob);

        Employee fromDb = employeeRepository.findById(bob.getId()).orElse(null);
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getName()).isEqualTo(bob.getName());
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Employee fromDb = employeeRepository.findById(-1L).orElse(null);
        assertThat(fromDb).isNull();
    }

    @Test
    public void givenListOfEmployees_whenFinAll_thenReturnAllEmployees() {
        List<Employee> allEmployees = EmployeeUtilTest.getEmployees();
        allEmployees.forEach(entityManager::persist);
        entityManager.flush();

        List<Employee> savedEmployees = employeeRepository.findAll();

        assertThat(savedEmployees).hasSize(3).extracting(Employee::getName)
                .containsExactlyElementsOf((Iterable<String>) () -> allEmployees.stream()
                        .map(Employee::getName).iterator());
    }
}