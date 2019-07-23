package com.github.pedrobacchini.springboottesting.repository;

import com.github.pedrobacchini.springboottesting.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByName(String name);
}
