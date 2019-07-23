package com.github.pedrobacchini.springboottesting.service;

import com.github.pedrobacchini.springboottesting.domain.Employee;

public interface EmployeeService {

    Employee getEmployeeByName(String name);
}
