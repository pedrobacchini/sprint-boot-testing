package com.github.pedrobacchini.springboottesting.service.impl;

import com.github.pedrobacchini.springboottesting.domain.Employee;
import com.github.pedrobacchini.springboottesting.repository.EmployeeRepository;
import com.github.pedrobacchini.springboottesting.service.EmployeeService;
import com.github.pedrobacchini.springboottesting.util.EmployeeUtilTest;
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

import java.util.List;
import java.util.Optional;

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

    @Before
    public void setUp() {
        Employee alex = EmployeeUtilTest.getAlex();
        Employee bob = EmployeeUtilTest.getBob();
        List<Employee> allEmployee = EmployeeUtilTest.getEmployees();

        //for whenValidName_thenEmployeeShouldBeFound
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(Optional.of(alex));

        //for whenValidName_thenEmployeeShouldExist
        Mockito.when(employeeRepository.findByName(bob.getName())).thenReturn(Optional.of(bob));

        //for given3Employees_whengetAll_theReturn3Records
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployee);
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String name = "alex";
        Employee found = employeeService.getEmployeeByName(name).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo(name);
    }

    @Test
    public void whenInValidName_thenEmployeeShouldNotBeFound() {
        Employee fromDb = employeeService.getEmployeeByName("wrong_name").orElse(null);
        assertThat(fromDb).isNull();
        verifyFindByNameIsCalledOnce("wrong_name");
    }

    @Test
    public void whenValidName_thenEmployeeShouldExist() {
        boolean doesEmployeeExist = employeeService.exists("bob");
        assertThat(doesEmployeeExist).isTrue();
        verifyFindByNameIsCalledOnce("bob");
    }

    @Test
    public void whenNonExistingName_thenEmployeeShouldNotExist() {
        boolean doesEmployeeExist = employeeService.exists("some_name");
        assertThat(doesEmployeeExist).isFalse();
        verifyFindByNameIsCalledOnce("some_name");
    }

    @Test
    public void given3Employees_whengetAll_theReturn3Records() {
        List<Employee> allEmployees = employeeService.getAllEmployees();
        verifyFindAllEmployeesIsCalledOnce();
        assertThat(allEmployees).hasSize(3).extracting(Employee::getName)
                .containsExactlyElementsOf((Iterable<String>) () -> allEmployees.stream()
                        .map(Employee::getName).iterator());
    }

    private void verifyFindByNameIsCalledOnce(String name) {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findByName(name);
        Mockito.reset(employeeRepository);
    }

    private void verifyFindAllEmployeesIsCalledOnce() {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(employeeRepository);
    }
}