package com.example.business.dtos;

import com.example.business.entities.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDto {
    private Integer id;
//    private EmployeeDto employeeDto;
    private Integer employeeId;
    private LocalDate date;
    private String status;
}
