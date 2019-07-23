package com.github.pedrobacchini.springboottesting.util;

import com.github.pedrobacchini.springboottesting.domain.Employee;

import java.util.Arrays;
import java.util.List;

public class EmployeeUtilTest {

    private static Employee alex = new Employee("alex");
    private static Employee bob = new Employee("bob");
    private static Employee otha = new Employee("otha");

    public static Employee getAlex() { return alex; }
    public static Employee getBob() { return bob; }
    public static Employee getOtha() { return otha; }

    private static Employee jhon = new Employee("jhon");
    private static Employee maria = new Employee("maria");
    private static Employee paula = new Employee("paula");

    public static List<Employee> getEmployees() { return Arrays.asList(jhon, maria, paula); }
}
