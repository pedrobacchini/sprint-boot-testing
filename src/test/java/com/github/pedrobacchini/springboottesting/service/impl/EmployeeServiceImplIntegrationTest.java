package com.github.pedrobacchini.springboottesting.service.impl;

import com.github.pedrobacchini.springboottesting.domain.Employee;
import com.github.pedrobacchini.springboottesting.repository.EmployeeRepository;
import com.github.pedrobacchini.springboottesting.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplIntegrationTest {

    @TestConfiguration
    static class EmployeeServiceEmplTestContextConfiguration {

        private final EmployeeRepository employeeRepository;

        EmployeeServiceEmplTestContextConfiguration(EmployeeRepository employeeRepository) {
            this.employeeRepository = employeeRepository;
        }

        @Bean
        public EmployeeService employeeService() { return new EmployeeServiceImpl(employeeRepository); }
    }

    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    private Employee alex = new Employee("alex");
    private Employee bob = new Employee("bob");
    private Employee jhon = new Employee("jhon");

    @Before
    public void setUp() {
        List<Employee> allEmployee = Arrays.asList(alex, jhon, bob);

        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployee);
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String name = "alex";

        Employee found = employeeService.getEmployeeByName(name);

        assertThat(found.getName())
                .isEqualTo(name);
    }

    @Test
    public void given3Employees_whengetAll_theReturn3Records() {

        List<Employee> allEmployees = employeeService.getAllEmployees();
        verifyFindAllEmployeesIsCalledOnce();
        assertThat(allEmployees).hasSize(3).extracting(Employee::getName)
                .containsExactly(alex.getName(), jhon.getName(), bob.getName());
    }

    private void verifyFindAllEmployeesIsCalledOnce() {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(employeeRepository);
    }
}