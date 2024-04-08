package com.example.business.mappers;

import com.example.business.dtos.EmployeeDto;
import com.example.business.entities.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EmployeeMapper {
    @Mapping(source = "job.id", target = "jobId")
    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "department.id", target = "departmentId")
    EmployeeDto entityToDto(Employee employee);

    @Mapping(target = "job.id", source = "jobId")
    @Mapping(target = "manager.id", source = "managerId")
    @Mapping(target = "department.id", source = "departmentId")
    Employee dtoToEntity(EmployeeDto employeeDto);
}
