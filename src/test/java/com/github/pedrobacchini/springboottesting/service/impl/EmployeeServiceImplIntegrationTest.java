package com.github.pedrobacchini.springboottesting.service.impl;

import com.github.pedrobacchini.springboottesting.domain.Employee;
import com.github.pedrobacchini.springboottesting.repository.EmployeeRepository;
import com.github.pedrobacchini.springboottesting.service.EmployeeService;
import com.github.pedrobacchini.springboottesting.util.EmployeeUtilTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

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

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        Employee alex = EmployeeUtilTest.getAlex();
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(Optional.of(alex));

        String name = "alex";
        Employee found = employeeService.getEmployeeByName(name).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo(name);
        verifyFindByNameIsCalledOnce(name);
    }

    @Test
    public void whenInValidName_thenEmployeeShouldNotBeFound() {
        String wrongName = "wrong_name";
        Optional<Employee> fromDb = employeeService.getEmployeeByName(wrongName);
        assertThat(fromDb.isPresent()).isFalse();
        verifyFindByNameIsCalledOnce(wrongName);
    }

    @Test
    public void whenValidName_thenEmployeeShouldExist() {
        Employee bob = EmployeeUtilTest.getBob();
        Mockito.when(employeeRepository.findByName(bob.getName())).thenReturn(Optional.of(bob));

        String name = "bob";
        boolean doesEmployeeExist = employeeService.exists(name);
        assertThat(doesEmployeeExist).isTrue();
        verifyFindByNameIsCalledOnce(name);
    }

    @Test
    public void whenNonExistingName_thenEmployeeShouldNotExist() {
        String someNome = "some_name";
        boolean doesEmployeeExist = employeeService.exists(someNome);
        assertThat(doesEmployeeExist).isFalse();
        verifyFindByNameIsCalledOnce(someNome);
    }

    @Test
    public void whenValidId_thenEmployeeShouldBeFound() {
        Employee otha = EmployeeUtilTest.getOtha();
        ReflectionTestUtils.setField(otha, "id", 111L);
        Mockito.when(employeeRepository.findById(otha.getId())).thenReturn(Optional.of(otha));

        Employee fromDb = employeeService.getEmployeeById(111L).orElse(null);
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getName()).isEqualTo(otha.getName());
        verifyFindByIdIsCalledOnce();
    }

    @Test
    public void whenInValidId_theEmployeeShouldNotBeFound() {
        Optional<Employee> fromDb = employeeService.getEmployeeById(-99L);
        assertThat(fromDb.isPresent()).isFalse();
        verifyFindByIdIsCalledOnce();
    }

    @Test
    public void given3Employees_whengetAll_theReturn3Records() {
        List<Employee> allEmployee = EmployeeUtilTest.getEmployees();
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployee);

        List<Employee> savedAllEmployees = employeeService.getAllEmployees();
        assertThat(savedAllEmployees).hasSize(3).extracting(Employee::getName)
                .containsExactlyElementsOf((Iterable<String>) () -> allEmployee.stream()
                        .map(Employee::getName).iterator());
        verifyFindAllEmployeesIsCalledOnce();
    }

    @Test
    public void whenSaveEmployee_thenEmployeeShouldReturn() {
        Employee alex = EmployeeUtilTest.getAlex();
        Mockito.when(employeeRepository.save(Mockito.any())).thenReturn(alex);

        Employee createdEmployee = employeeService.createEmployee(alex);
        assertThat(createdEmployee.getName()).isEqualTo(alex.getName());
        verifySaveIsCalledOnce(alex);
    }

    private void verifyFindByNameIsCalledOnce(String name) {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findByName(name);
        Mockito.reset(employeeRepository);
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
        Mockito.reset(employeeRepository);
    }

    private void verifyFindAllEmployeesIsCalledOnce() {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(employeeRepository);
    }

    private void verifySaveIsCalledOnce(Employee employee) {
        Mockito.verify(employeeRepository, VerificationModeFactory.times(1)).save(employee);
        Mockito.reset(employeeRepository);
    }
}