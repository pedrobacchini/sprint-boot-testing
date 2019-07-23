package com.github.pedrobacchini.springboottesting.repository;

import com.github.pedrobacchini.springboottesting.domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void whenFindByName_theReturnEmployee() {
        //given
        Employee alex = new Employee("alex");
        entityManager.persist(alex);
        entityManager.flush();

        //when
        Employee found = employeeRepository.findByName(alex.getName());

        // then
        assertThat(found.getName())
                .isEqualTo(alex.getName());
    }
}