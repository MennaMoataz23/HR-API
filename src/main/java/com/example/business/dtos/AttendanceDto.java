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
    private EmployeeDto employeeDto;
    private LocalDate date;
    private EmployeeStatus status;
}
