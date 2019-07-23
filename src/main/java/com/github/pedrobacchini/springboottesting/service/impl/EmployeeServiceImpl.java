package com.github.pedrobacchini.springboottesting.service.impl;

import com.github.pedrobacchini.springboottesting.domain.Employee;
import com.github.pedrobacchini.springboottesting.repository.EmployeeRepository;
import com.github.pedrobacchini.springboottesting.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee getEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }
}
