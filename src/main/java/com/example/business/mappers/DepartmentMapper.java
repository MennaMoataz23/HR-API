package com.example.business.mappers;

import com.example.business.dtos.DepartmentDto;
import com.example.business.dtos.EmployeeDto;
import com.example.business.entities.Department;
import com.example.business.entities.Employee;
import org.mapstruct.Mapper;

@Mapper
public interface DepartmentMapper {
    DepartmentDto entityToDto(Department department);

    Department dtoToEntity(DepartmentDto departmentDto);
}
