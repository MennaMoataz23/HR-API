package com.example.business.dtos;

import lombok.*;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Double salary;
    private Date hireDate;
}
