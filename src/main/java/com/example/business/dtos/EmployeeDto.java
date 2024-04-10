package com.example.business.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Double salary;
    private LocalDate hireDate;
    private Integer jobId;
    private Integer managerId;
    private Integer departmentId;
}
