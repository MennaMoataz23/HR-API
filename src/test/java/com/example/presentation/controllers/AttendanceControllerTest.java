package com.example.presentation.controllers;

import com.example.business.dtos.AttendanceDto;
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

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
public class AttendanceControllerTest {
    JobService jobService = new JobService();
    EmployeeService employeeService = new EmployeeService();
    AttendanceService attendanceService = new AttendanceService();
    @Test
    void Get_all_attendance_returns_200_Ok() {
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/attendances")
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        assertEquals(response.getStatus(), 200);

        client.close();
    }

    @Test
    void get_attendance_returns_200_when_attendance_is_present() {
        JobDto job = JobDto.builder()
                .title("Software Engineer")
                .build();
        JobDto createdJob = jobService.addJob(job);

        System.out.println(createdJob.getId());

        EmployeeDto employee = EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123")
                .salary(4025.35)
                .jobId(createdJob.getId())
                .hireDate(LocalDate.parse("2024-03-08"))
                .build();

        EmployeeDto createdEmployee = employeeService.addEmployee(employee);

        AttendanceDto attendance = AttendanceDto.builder()
                .date(LocalDate.parse("2022-01-01"))
                .status("Present")
                .employeeId(createdEmployee.getId())
                .build();
        AttendanceDto createdAttendance = attendanceService.addAttendance(attendance);

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/attendances")
                .path("{id}")
                .resolveTemplate("id", createdAttendance.getId())
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        assertEquals(response.getStatus(), 200);

        attendanceService.deleteAttendance(createdAttendance.getId());
        employeeService.deleteEmployee(createdEmployee.getId());
        jobService.deleteJob(createdJob.getId());
        client.close();
    }

    @Test
    void get_attendance_returns_404_when_attendance_is_not_present() {

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/attendances")
                .path("{id}")
                .resolveTemplate("id", 145674465)
                .request(MediaType.APPLICATION_JSON)
                .get(Response.class);

        assertEquals(response.getStatus(), 404);
        client.close();
    }

    @Test
    void create_attendance_returns_201_on_success() {
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

        AttendanceDto attendance = AttendanceDto.builder()
                .date(LocalDate.parse("2022-01-01"))
                .status("Present")
                .employeeId(createdEmployee.getId())
                .build();


        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/attendances")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(attendance, MediaType.APPLICATION_JSON));

        assertEquals(response.getStatus(), 201);

        attendanceService.deleteAttendance(response.readEntity(AttendanceDto.class).getId());
        employeeService.deleteEmployee(createdEmployee.getId());
        jobService.deleteJob(createdJob.getId());

        client.close();
    }


    @Test
    void update_attendance_returns_200_on_success() {
        JobDto job = JobDto.builder()
                .title("Software Engineer")
                .build();
        JobDto createdJob = jobService.addJob(job);

        EmployeeDto employee = EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123")
                .salary(40000.00)
                .jobId(createdJob.getId())
                .hireDate(LocalDate.parse("2024-03-08"))
                .build();

        EmployeeDto createdEmployee = employeeService.addEmployee(employee);

        AttendanceDto attendance = AttendanceDto.builder()
                .date(LocalDate.parse("2022-01-01"))
                .status("Present")
                .employeeId(createdEmployee.getId())
                .build();

        AttendanceDto createdAttendance = attendanceService.addAttendance(attendance);


        createdAttendance.setStatus("Late");

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/attendances")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(createdAttendance, MediaType.APPLICATION_JSON));

        assertEquals(response.getStatus(), 200);


        attendanceService.deleteAttendance(createdAttendance.getId());
        employeeService.deleteEmployee(createdEmployee.getId());
        jobService.deleteJob(createdJob.getId());

        client.close();

    }

    @Test
    void update_attendance_returns_400_when_attendance_is_not_present() {
        AttendanceDto attendance = AttendanceDto.builder()
                .id(45644)
                .date(LocalDate.parse("2022-01-01"))
                .status("Present")
                .employeeId(859034)
                .build();

        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/attendances")
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(attendance, MediaType.APPLICATION_JSON));
        assertEquals(response.getStatus(), 404);

        client.close();
    }

    @Test
    void delete_attendance_returns_200_on_success() {

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

        EmployeeDto createdEmployee = employeeService.addEmployee(employee);

        AttendanceDto attendance = AttendanceDto.builder()
                .date(LocalDate.parse("2022-01-01"))
                .status("Present")
                .employeeId(createdEmployee.getId())
                .build();
        AttendanceDto createdAttendance = attendanceService.addAttendance(attendance);

        // test
        Client client = ClientBuilder.newClient();
        Response response = client
                .target("http://localhost:9090/hr/api/attendances")
                .path("{id}")
                .resolveTemplate("id", createdAttendance.getId())
                .request(MediaType.APPLICATION_JSON)
                .delete(Response.class);

        // assert
        assertEquals(response.getStatus(), 200);

        employeeService.deleteEmployee(createdEmployee.getId());
        jobService.deleteJob(createdJob.getId());

        client.close();
    }

}
