package com.github.pedrobacchini.springboottesting.resource;

import com.github.pedrobacchini.springboottesting.domain.Employee;
import com.github.pedrobacchini.springboottesting.service.EmployeeService;
import com.github.pedrobacchini.springboottesting.util.EmployeeUtilTest;
import com.github.pedrobacchini.springboottesting.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeResource.class)
public class EmployeeResourceIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    private Employee alex = EmployeeUtilTest.getAlex();
    private List<Employee> allEmployee = EmployeeUtilTest.getEmployees();

    @Before
    public void setUp() {
        given(employeeService.getEmployeeByName(alex.getName())).willReturn(Optional.of(alex));
        given(employeeService.getAllEmployees()).willReturn(allEmployee);
        given(employeeService.createEmployee(Mockito.any())).willReturn(alex);
    }

    @Test
    public void whenPostEmployee_thenCreateEmployee() throws Exception {
        mvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(alex)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(alex.getName())));
        verify(employeeService, VerificationModeFactory.times(1)).createEmployee(Mockito.any());
        reset(employeeService);
    }

    @Test
    public void givenEmployee_whenGetEmployeeByName_thenReturnEmployeeJson() throws Exception {
        mvc.perform(get("/api/employees/{name}", alex.getName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(alex.getName())));
        verify(employeeService, VerificationModeFactory.times(1)).getEmployeeByName(Mockito.any());
        reset(employeeService);
    }

    @Test
    public void givenEmployees_whenGetEmployees_thenReturnJsonArray() throws Exception {
        Employee firstEmployee = allEmployee.get(0);
        Employee secondEmployee = allEmployee.get(1);
        Employee thirdEmployee = allEmployee.get(2);

        mvc.perform(get("/api/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(firstEmployee.getName())))
                .andExpect(jsonPath("$[1].name", is(secondEmployee.getName())))
                .andExpect(jsonPath("$[2].name", is(thirdEmployee.getName())));
        verify(employeeService, VerificationModeFactory.times(1)).getAllEmployees();
        reset(employeeService);
    }
}