package com.example.presentation.soap;

import com.example.business.dtos.JobDto;
import com.example.business.services.JobService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService
public class JobWebService {
    private final JobService service = new JobService();

    @WebMethod
    public JobDto getJob(@WebParam(name = "id") int jobId) {
        return service.getJobById(jobId);
    }

    @WebMethod
    public JobDto createJob(@WebParam(name = "job") JobDto job) {
        return service.addJob(job);
    }

    @WebMethod
    public String deleteJob(@WebParam(name = "id") int jobId) {
        service.deleteJob(jobId);
        return "Job with ID: " + jobId + " has been deleted";
    }

    @WebMethod
    public JobDto updateJob(@WebParam(name = "id") int jobId, @WebParam(name = "job") JobDto jobDto) {
        jobDto.setId(jobId); // Ensure the ID is set for update
        return service.updateJob(jobDto);
    }
}
