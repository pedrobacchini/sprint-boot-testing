package com.github.pedrobacchini.springboottesting.resource;

import com.github.pedrobacchini.springboottesting.domain.Employee;
import com.github.pedrobacchini.springboottesting.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) { this.employeeService = employeeService; }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() { return employeeService.getAllEmployees(); }
}
