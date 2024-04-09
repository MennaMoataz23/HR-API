package com.example.business.services;

import com.example.business.dtos.EmployeeDto;
import com.example.business.dtos.JobDto;
import com.example.business.entities.Employee;
import com.example.business.entities.Job;
import com.example.business.mappers.EmployeeMapper;
import com.example.business.mappers.EmployeeMapperImpl;
import com.example.business.mappers.JobMapper;
import com.example.business.mappers.JobMapperImpl;
import com.example.persistence.Database;
import com.example.persistence.daos.JobDao;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class JobService {
    private final EntityManagerFactory entityManagerFactory;
    private final JobDao jobDao = JobDao.getInstance();

    public JobService(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<JobDto> getAllJobs(){
        return Database.doInTransaction(entityManager -> {
            JobMapper mapper = new JobMapperImpl();
            List<Job> jobs = jobDao.findAll(entityManager);
            List<JobDto> jobDtoList = new ArrayList<>();
            jobs.forEach(job -> jobDtoList.add(mapper.entityToDto(job)));
            return jobDtoList;
        });
    }

    public JobDto getJobById(int id){
        return Database.doInTransaction(entityManager -> {
            JobMapper mapper = new JobMapperImpl();
            Job job = jobDao.findOneById(id, entityManager).orElse(null);
            if (job != null){
                return mapper.entityToDto(job);
            }else {
                return null;
            }
        });
    }

    public JobDto addJob(JobDto jobDto){
        Database.doInTransactionWithoutResult(entityManager -> {
            JobMapper mapper = new JobMapperImpl();
            Job job = mapper.dtoToEntity(jobDto);
            jobDao.create(entityManager, job);
        });
        return jobDto;
    }

    public boolean deleteJob(int id){
        return Database.doInTransaction(entityManager -> {
            if (jobDao.findOneById(id, entityManager) != null){
                jobDao.deleteById(entityManager, id);
                return true;
            }else {
                return false;
            }
        });
    }

    public JobDto updateJob(JobDto jobDto) {
        return Database.doInTransaction(entityManager -> {
            JobMapper mapper = new JobMapperImpl();
            Job existingJob = jobDao.findOneById(jobDto.getId(), entityManager).orElse(null);
            if (existingJob != null) {
                existingJob.setTitle(jobDto.getTitle());
                jobDao.update(entityManager, existingJob);
                return mapper.entityToDto(existingJob);
            } else {
                return null;
            }
        });
    }
}
