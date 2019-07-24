package com.github.pedrobacchini.springboottesting.service.impl;

import com.github.pedrobacchini.springboottesting.domain.Employee;
import com.github.pedrobacchini.springboottesting.repository.EmployeeRepository;
import com.github.pedrobacchini.springboottesting.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) { this.employeeRepository = employeeRepository; }

    @Override
    public Optional<Employee> getEmployeeByName(String name) { return employeeRepository.findByName(name); }

    @Override
    public List<Employee> getAllEmployees() { return employeeRepository.findAll(); }

    @Override
    public boolean exists(String name) { return employeeRepository.findByName(name).isPresent(); }

    @Override
    public Optional<Employee> getEmployeeById(Long id) { return employeeRepository.findById(id); }

    @Override
    public Employee createEmployee(Employee employee) { return employeeRepository.save(employee); }
}
