package org.example.bussiness.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "department")
public class Department {
    @Id
    @Column(name = "department_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "location", length = 45)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dep_managerId")
    private Employee departmentManager;
}