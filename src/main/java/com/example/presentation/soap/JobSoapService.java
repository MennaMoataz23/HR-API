//package com.example.presentation.soap;
//
//import com.example.business.dtos.JobDto;
//import com.example.business.services.JobService;
//import jakarta.persistence.EntityManagerFactory;
//import jakarta.persistence.Persistence;
//
//import javax.jws.WebMethod;
//import javax.jws.WebParam;
//import javax.jws.WebService;
//
//@WebService
//public class JobSoapService {
//    private JobService service;
//    private EntityManagerFactory entityManagerFactory;
//
//
//    public JobSoapService() {
//        entityManagerFactory = Persistence.createEntityManagerFactory("entityManagerFactory");
//        service = new JobService(entityManagerFactory);
//    }
//
//    @WebMethod
//    public JobDto getJob(@WebParam(name = "id") int jobId) {
//        return service.getJobById(jobId);
//    }
//
//    @WebMethod
//    public JobDto createJob(@WebParam(name = "job") JobDto job) {
//        return service.addJob(job);
//    }
//
//    @WebMethod
//    public String deleteJob(@WebParam(name = "id") int jobId) {
//        service.deleteJob(jobId);
//        return "Job with ID: " + jobId + " has been deleted";
//    }
//
//    @WebMethod
//    public JobDto updateJob(@WebParam(name = "id") int jobId, @WebParam(name = "job") JobDto jobDto) {
//        jobDto.setId(jobId); // Ensure the ID is set for update
//        return service.updateJob(jobDto);
//    }
//}
