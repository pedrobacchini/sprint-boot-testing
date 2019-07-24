package com.github.pedrobacchini.springboottesting.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Getter
@Table(name = "person")
@NoArgsConstructor(access = AccessLevel.PRIVATE) //For Hibernate
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 20)
    private String name;

    public Employee(@Size(min = 3, max = 20) String name) { this.name = name; }
}
