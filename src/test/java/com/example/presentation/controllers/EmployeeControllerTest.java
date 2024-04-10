package com.example.presentation.controllers;

import com.example.business.dtos.EmployeeDto;
import com.example.business.dtos.JobDto;
import com.example.business.services.AttendanceService;
import com.example.business.services.EmployeeService;
import com.example.business.services.JobService;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeControllerTest {
    JobService jobService = new JobService();
    EmployeeService employeeService = new EmployeeService();
    AttendanceService attendanceService = new AttendanceService();
    @Test
    void Get_all_employees_returns_200_Ok() {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/employees")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        assertEquals(200, response.getStatus());

        client.close();
    }

    @Test
    void get_employee_returns_200_when_employee_is_present() {

        JobDto job = JobDto.builder()
                .title("Software Engineer")
                .build();
        JobDto createdJob = jobService.addJob(job);

        EmployeeDto employee = EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123")
                .salary(40000.0)
                .jobId(createdJob.getId())
                .hireDate(LocalDate.parse("2024-03-08"))
                .build();

        EmployeeDto createdEmployee = employeeService.addEmployee(employee);



        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/employees")
                .path("{id}")
                .resolveTemplate("id", createdEmployee.getId())
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        assertEquals(200, response.getStatus());


        employeeService.deleteEmployee(createdEmployee.getId());
        jobService.deleteJob(createdJob.getId());
        client.close();
    }

    @Test
    void get_department_returns_404_when_department_is_not_present() {

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/employees")
                .path("{id}")
                .resolveTemplate("id", 145674465)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        assertEquals(404, response.getStatus());
        client.close();
    }

    @Test
    void create_employee_returns_201_on_success() {
        JobDto job = JobDto.builder()
                .title("Software Engineer")
                .build();
        JobDto createdJob = jobService.addJob(job);

        EmployeeDto employee = EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123")
                .salary(30000.0)
                .jobId(createdJob.getId())
                .hireDate(LocalDate.parse("2024-03-08"))
                .build();

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/employees")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(employee, MediaType.APPLICATION_JSON));

        assertEquals(201, response.getStatus());


        employeeService.deleteEmployee(response.readEntity(EmployeeDto.class).getId());
        jobService.deleteJob(createdJob.getId());

        client.close();
    }


    @Test
    void update_employee_returns_200_on_success() {
        JobDto job = JobDto.builder()
                .title("Software Engineer")
                .build();
        JobDto createdJob = jobService.addJob(job);

        EmployeeDto employee = EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123")
                .salary(50000.0)
                .jobId(createdJob.getId())
                .hireDate(LocalDate.parse("2024-03-08"))
                .build();

        EmployeeDto createdEmployee = employeeService.addEmployee(employee);

        createdEmployee.setFirstName("Jane");

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/employees")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(createdEmployee, MediaType.APPLICATION_JSON));

        assertEquals(200, response.getStatus());


        employeeService.deleteEmployee(createdEmployee.getId());
        jobService.deleteJob(createdJob.getId());

        client.close();
    }

    @Test
    void update_employee_returns_400_when_employee_is_not_present() {

        EmployeeDto employee = EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123")
                .salary(50000.0)
                .hireDate(LocalDate.parse("2024-03-08"))
                .build();

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/employees")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(employee, MediaType.APPLICATION_JSON));

        assertEquals(400, response.getStatus());
        client.close();
    }

    @Test
    void delete_employee_returns_200_on_success() {

        JobDto job = JobDto.builder()
                .title("Software Engineer")
                .build();
        JobDto createdJob = jobService.addJob(job);

        EmployeeDto employee = EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123")
                .salary(50000.0)
                .jobId(createdJob.getId())
                .hireDate(LocalDate.parse("2024-03-08"))
                .build();

        EmployeeDto createdEmployee = employeeService.addEmployee(employee);


        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/employees")
                .path("{id}")
                .resolveTemplate("id", createdEmployee.getId())
                .request(MediaType.APPLICATION_JSON)
                .delete(Response.class);

        assertEquals(200, response.getStatus());

        jobService.deleteJob(createdJob.getId());

        client.close();
    }
}
