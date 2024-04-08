package com.example.business.mappers;

import com.example.business.dtos.AttendanceDto;
import com.example.business.dtos.DepartmentDto;
import com.example.business.entities.Attendance;
import com.example.business.entities.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AttendanceMapper {
    @Mapping(source = "employee.id", target = "employeeId")
    AttendanceDto entityToDto(Attendance attendance);
    @Mapping(source = "employeeId", target = "employee.id")
    Attendance dtoToEntity(AttendanceDto attendanceDto);
}
