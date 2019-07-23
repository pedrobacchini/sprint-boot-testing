package com.github.pedrobacchini.springboottesting.service;

import com.github.pedrobacchini.springboottesting.domain.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Optional<Employee> getEmployeeByName(String name);

    List<Employee> getAllEmployees();

    boolean exists(String name);
}
