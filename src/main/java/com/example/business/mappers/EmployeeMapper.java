package com.example.business.mappers;

import com.example.business.dtos.EmployeeDto;
import com.example.business.entities.Employee;
import org.mapstruct.Mapper;

@Mapper
public interface EmployeeMapper {
    EmployeeDto entityToDto(Employee employee);

    Employee dtoToEntity(EmployeeDto employeeDto);
}
