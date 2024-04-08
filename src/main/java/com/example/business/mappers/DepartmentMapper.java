package com.example.business.mappers;

import com.example.business.dtos.DepartmentDto;
import com.example.business.dtos.EmployeeDto;
import com.example.business.entities.Department;
import com.example.business.entities.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DepartmentMapper {
    @Mapping(source = "departmentManager.id", target = "employeeId")
    DepartmentDto entityToDto(Department department);

    @Mapping(source = "employeeId", target = "departmentManager.id")
    Department dtoToEntity(DepartmentDto departmentDto);
}
