package com.github.pedrobacchini.springboottesting.resource;

import com.github.pedrobacchini.springboottesting.domain.Employee;
import com.github.pedrobacchini.springboottesting.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeResource.class)
public class EmployeeResourceIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    private Employee alex = new Employee("alex");

    @Before
    public void setUp() {
        List<Employee> allEmployee = Collections.singletonList(alex);
        given(employeeService.getEmployeeByName(alex.getName())).willReturn(alex);
        given(employeeService.getAllEmployees()).willReturn(allEmployee);
    }

    @Test
    public void givenEmployee_whenGetEmployeeByName_thenReturnEmployeeJson() throws Exception {
        mvc.perform(get("/api/employees/{name}", alex.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(alex.getName())));
    }

    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray() throws Exception {
        mvc.perform(get("/api/employees").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(alex.getName())));
    }
}