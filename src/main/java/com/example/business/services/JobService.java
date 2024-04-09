package com.example.business.services;

import com.example.business.dtos.JobDto;
import com.example.business.entities.Job;
import com.example.business.mappers.JobMapper;
import com.example.business.mappers.JobMapperImpl;
import com.example.persistence.Database;
import com.example.persistence.daos.JobDao;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class JobService {
    private final JobDao jobDao = JobDao.getInstance();

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
            Job job = jobDao
                    .findOneById(id, entityManager)
                    .orElseThrow(()-> new NoSuchElementException("Job with ID: " + id +" Not Found"));
            return mapper.entityToDto(job);
        });
    }

    public JobDto addJob(JobDto jobDto){
        Database.doInTransactionWithoutResult(entityManager -> {
            JobMapper mapper = new JobMapperImpl();
            Job job = mapper.dtoToEntity(jobDto);
            jobDao.create(entityManager, job);
            jobDto.setId(job.getId());
        });
        return jobDto;
    }

    public boolean deleteJob(int id){
        return Database.doInTransaction(entityManager -> {
            Job job = jobDao
                    .findOneById(id, entityManager)
                    .orElseThrow(()-> new NoSuchElementException("Job with ID: " + id +" Not Found"));
            jobDao.deleteById(entityManager, id);
            return true;
        });
    }

    public JobDto updateJob(JobDto jobDto) {
        return Database.doInTransaction(entityManager -> {
            JobMapper mapper = new JobMapperImpl();
            Job existingJob = jobDao
                    .findOneById(jobDto.getId(), entityManager)
                    .orElseThrow(()-> new NoSuchElementException("Job with ID: " + jobDto.getId() +" Not Found"));

            existingJob.setTitle(jobDto.getTitle());
            jobDao.update(entityManager, existingJob);
            return mapper.entityToDto(existingJob);
        });
    }
}
