package com.example.presentation.controllers;

import com.example.business.dtos.AttendanceDto;
import com.example.business.dtos.DepartmentDto;
import com.example.business.dtos.EmployeeDto;
import com.example.business.services.AttendanceService;
import com.example.business.services.DepartmentService;
import com.example.business.services.EmployeeService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("attendances")
public class AttendanceController {
    EntityManagerFactory entityManagerFactory;
    AttendanceService service;

    @GET
    public Response getAllAttendance(){
        service = new AttendanceService(entityManagerFactory);
        List<AttendanceDto> attendanceDtoList = service.getAllAttendance();
        if (attendanceDtoList != null){
            return Response.ok().entity(attendanceDtoList).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("{id}")
    public Response getDepartment(@PathParam("id") int attendanceId){
        service = new AttendanceService(entityManagerFactory);
        AttendanceDto attendanceDto = service.getAttendanceById(attendanceId);
        return Response.ok().entity(attendanceDto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createAttendance(AttendanceDto attendance){
        service = new AttendanceService(entityManagerFactory);
        AttendanceDto attendanceDto = service.addAttendance(attendance);
        if (attendanceDto != null){
            return Response.ok().entity(attendanceDto).build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteAttendance(@PathParam("id") int attendanceId){
        service = new AttendanceService(entityManagerFactory);
        AttendanceDto attendanceDto = service.getAttendanceById(attendanceId);
        if (attendanceDto != null){
            service.deleteAttendance(attendanceId);
            return Response.ok().build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateAttendance(@PathParam("id") Integer attendanceId, AttendanceDto attendanceDto){
        service = new AttendanceService(entityManagerFactory);
        service.updateAttendance(attendanceDto);
        return Response.ok().entity(attendanceDto).build();
    }
}
