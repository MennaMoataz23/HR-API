package org.example.bussiness.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Integer id;

    @Column(name = "first_name", length = 45)
    private String firstName;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "phone_number", length = 45)
    private String phoneNumber;

    @Column(name = "hire_date")
    private Instant hireDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @Column(name = "department_id")
    private Integer departmentId;

    @OneToMany(mappedBy = "employee")
    private Set<Attendance> attendances = new LinkedHashSet<>();

    @OneToMany(mappedBy = "depManager")
    private Set<Department> departments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "manager")
    private Set<Employee> employees = new LinkedHashSet<>();

    @OneToMany(mappedBy = "employee")
    private Set<Salary> salaries = new LinkedHashSet<>();

}