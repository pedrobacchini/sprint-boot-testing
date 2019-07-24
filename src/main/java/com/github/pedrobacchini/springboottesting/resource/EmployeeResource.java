package com.github.pedrobacchini.springboottesting.resource;

import com.github.pedrobacchini.springboottesting.domain.Employee;
import com.github.pedrobacchini.springboottesting.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) { this.employeeService = employeeService; }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() { return employeeService.getAllEmployees(); }

    @GetMapping("/employees/{name}")
    public Optional<Employee> getEmployeeByName(@PathVariable String name) {
        return employeeService.getEmployeeByName(name);
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) { return employeeService.createEmployee(employee); }
}
