package com.example.presentation.controllers;

import com.example.business.dtos.EmployeeDto;
import com.example.business.dtos.JobDto;
import com.example.business.services.EmployeeService;
import com.example.business.services.JobService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("jobs")
public class JobController {
    EntityManagerFactory entityManagerFactory;
    JobService service;

    static {
        System.out.println("jobs controller");
    }

    @GET
    public Response getAllJobs(){
        service = new JobService(entityManagerFactory);
        List<JobDto> jobDtoList = service.getAllJobs();
        if (jobDtoList != null){
            return Response.ok().entity(jobDtoList).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("{id}")
    public Response getJob(@PathParam("id") int jobId){
        service = new JobService(entityManagerFactory);
        JobDto jobDto = service.getJobById(jobId);
        if (jobDto != null){
            return Response.ok().entity(jobDto).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createJob(JobDto job){
        service = new JobService(entityManagerFactory);
        JobDto jobDto = service.addJob(job);
        if (jobDto != null){
            return Response.ok().entity(jobDto).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteJob(@PathParam("id") int jobId){
        service = new JobService(entityManagerFactory);
        boolean jobDeleted = service.deleteJob(jobId);
        if (jobDeleted){
            return Response.ok().build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateJob(@PathParam("id") int jobId, JobDto jobDto) {
        service = new JobService(entityManagerFactory);
        JobDto updatedJobDto = service.updateJob(jobDto);
        if (updatedJobDto != null) {
            return Response.ok().entity(updatedJobDto).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
