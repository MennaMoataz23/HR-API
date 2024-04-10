package com.example.presentation.controllers;

import com.example.business.dtos.DepartmentDto;
import com.example.business.dtos.EmployeeDto;
import com.example.business.dtos.JobDto;
import com.example.business.services.DepartmentService;
import com.example.business.services.EmployeeService;
import com.example.business.services.JobService;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DepartmentControllerTest {
    EmployeeService employeeService = new EmployeeService();
    JobService jobService = new JobService();
    DepartmentService departmentService = new DepartmentService();
    @Test
    void Get_all_departments_returns_200_Ok() {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/departments")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        assertEquals(200, response.getStatus());

        client.close();
    }

    @Test
    void get_departments_returns_200_when_department_is_present() {

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

        DepartmentDto department = DepartmentDto.builder()
                .name("IT")
                .location("New York")
                .employeeId(createdEmployee.getId())
                .build();
        DepartmentDto createdDepartment = departmentService.addDepartment(department);


        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/departments")
                .path("{id}")
                .resolveTemplate("id", createdDepartment.getId())
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        assertEquals(200, response.getStatus());

        departmentService.deleteDepartment(createdDepartment.getId());
        employeeService.deleteEmployee(createdEmployee.getId());
        jobService.deleteJob(createdJob.getId());
        client.close();
    }

    @Test
    void get_department_returns_404_when_department_is_not_present() {

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/vacations")
                .path("{id}")
                .resolveTemplate("id", 145674465)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        assertEquals(404, response.getStatus());
        client.close();
    }

    @Test
    void create_department_returns_201_on_success() {
        JobDto job = JobDto.builder()
                .title("Software Engineer")
                .build();
        JobDto createdJob = jobService.addJob(job);

        EmployeeDto employee = EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123")
                .salary(3000.0)
                .jobId(createdJob.getId())
                .hireDate(LocalDate.parse("2024-03-08"))
                .build();

        EmployeeDto createdEmployee = employeeService.addEmployee(employee);

        DepartmentDto department = DepartmentDto.builder()
                .name("IT")
                .location("New York")
                .employeeId(createdEmployee.getId())
                .build();


        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/departments")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(department, MediaType.APPLICATION_JSON));

        assertEquals(201, response.getStatus());

        departmentService.deleteDepartment(response.readEntity(DepartmentDto.class).getId());
        employeeService.deleteEmployee(createdEmployee.getId());
        jobService.deleteJob(createdJob.getId());

        client.close();
    }


    @Test
    void update_department_returns_200_on_success() {
        JobDto job = JobDto.builder()
                .title("Software Engineer")
                .build();
        JobDto createdJob = jobService.addJob(job);

        EmployeeDto employee = EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123")
                .salary(4000.0)
                .jobId(createdJob.getId())
                .hireDate(LocalDate.parse("2024-03-08"))
                .build();

        EmployeeDto createdEmployee = employeeService.addEmployee(employee);

        DepartmentDto department = DepartmentDto.builder()
                .name("IT")
                .location("New York")
                .employeeId(createdEmployee.getId())
                .build();

        DepartmentDto createdDepartment = departmentService.addDepartment(department);


        createdDepartment.setLocation("London");

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/departments")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(createdDepartment, MediaType.APPLICATION_JSON));

        assertEquals(200, response.getStatus());


        departmentService.deleteDepartment(createdDepartment.getId());
        employeeService.deleteEmployee(createdEmployee.getId());
        jobService.deleteJob(createdJob.getId());

        client.close();

    }

    @Test
    void delete_department_returns_200_on_success() {

        JobDto job = JobDto.builder()
                .title("Software Engineer")
                .build();
        JobDto createdJob = jobService.addJob(job);

        EmployeeDto employee = EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123")
                .salary(4000.0)
                .jobId(createdJob.getId())
                .hireDate(LocalDate.parse("2024-03-08"))
                .build();

        EmployeeDto createdEmployee = employeeService.addEmployee(employee);

        DepartmentDto department = DepartmentDto.builder()
                .name("IT")
                .location("New York")
                .employeeId(createdEmployee.getId())
                .build();

        DepartmentDto createdDepartment = departmentService.addDepartment(department);




        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/departments")
                .path("{id}")
                .resolveTemplate("id", createdDepartment.getId())
                .request(MediaType.APPLICATION_JSON)
                .delete(Response.class);

        assertEquals(200, response.getStatus());

        employeeService.deleteEmployee(createdEmployee.getId());
        jobService.deleteJob(createdJob.getId());

        client.close();
    }
}
