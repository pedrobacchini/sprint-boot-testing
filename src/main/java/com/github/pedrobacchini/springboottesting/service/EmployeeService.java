package com.github.pedrobacchini.springboottesting.service;

import com.github.pedrobacchini.springboottesting.domain.Employee;

import java.util.List;

public interface EmployeeService {

    Employee getEmployeeByName(String name);

    List<Employee> getAllEmployees();
}
