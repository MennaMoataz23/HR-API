package com.example.presentation.controllers;

import com.example.business.dtos.JobDto;
import com.example.business.services.JobService;
import com.example.persistence.Database;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JobControllerTest {

    JobService jobService = new JobService();
    @Test
    void Get_all_jobs_returns_200_Ok() {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/jobs")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        assertEquals(response.getStatus(), 200);

        client.close();
    }

    @Test
    void get_job_returns_200_when_job_is_present() {
        Database.doInTransactionWithoutResult(entityManager -> {
            JobDto job = JobDto.builder()
                    .title("Software Engineer")
                    .build();
            JobDto createdJob = jobService.addJob(job);

            Client client = ClientBuilder.newClient();
            Response response = client
                    .target("http://localhost:9090/hr/api/jobs")
                    .path("{id}")
                    .resolveTemplate("id", createdJob.getId())
                    .request(MediaType.APPLICATION_JSON)
                    .get(Response.class);

            assertEquals(response.getStatus(), 200);

            jobService.deleteJob(createdJob.getId());
            client.close();
        });
    }


    @Test
    void get_job_returns_404_when_job_is_not_present() {

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/jobs")
                .path("{id}")
                .resolveTemplate("id", 145674465)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        assertEquals(response.getStatus(), 404);
        client.close();
    }

    @Test
    void create_job_returns_201_on_success() {
        JobDto job = JobDto.builder()
                .title("Software Engineer")
                .build();

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/jobs")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(job, MediaType.APPLICATION_JSON));

        assertEquals(response.getStatus(), 200);

        jobService.deleteJob(response.readEntity(JobDto.class).getId());

        client.close();
    }

    @Test
    void update_job_returns_200_on_success() {
        JobDto job = JobDto.builder()
                .title("Software Engineer")
                .build();

        JobDto testJob = jobService.addJob(job);


        testJob.setTitle("Software Engineer II");

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/jobs")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(testJob, MediaType.APPLICATION_JSON));

        assertEquals(response.getStatus(), 200);


        jobService.deleteJob(testJob.getId());
        client.close();

    }

    @Test
    void update_job_returns_400_when_job_is_not_present() {
        JobDto job = JobDto.builder()
                .id(859034)
                .title("Software Engineer")
                .build();


        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/jobs")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(job, MediaType.APPLICATION_JSON));

        assertEquals(response.getStatus(), 404);
        client.close();
    }

    @Test
    void delete_job_returns_200_on_success() {
        // arrange
        JobDto job = JobDto.builder()
                .title("Software Engineer")
                .build();
        JobDto createdJob = jobService.addJob(job);

        // test
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/jobs")
                .path("{id}")
                .resolveTemplate("id", createdJob.getId())
                .request(MediaType.APPLICATION_JSON)
                .delete(Response.class);

        // assert
        assertEquals(response.getStatus(), 200);

        client.close();
    }

}