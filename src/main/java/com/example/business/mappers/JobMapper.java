package com.example.business.mappers;

import com.example.business.dtos.EmployeeDto;
import com.example.business.dtos.JobDto;
import com.example.business.entities.Employee;
import com.example.business.entities.Job;
import org.mapstruct.Mapper;

@Mapper
public interface JobMapper {
    JobDto entityToDto(Job job);

    Job dtoToEntity(JobDto jobDto);
}
