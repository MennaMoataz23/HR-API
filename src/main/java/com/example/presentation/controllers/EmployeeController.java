package com.example.presentation.controllers;

import com.example.business.dtos.DepartmentDto;
import com.example.business.dtos.EmployeeDto;
//import com.example.business.services.EmployeeService;
import com.example.business.services.DepartmentService;
import com.example.business.services.EmployeeService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.BadRequestException;

@Path("employees")
public class EmployeeController {
    EntityManagerFactory entityManagerFactory;
    EmployeeService service;

    @GET
    @Path("{id}")
    public Response getEmployee(@PathParam("id") int empId){
        service = new EmployeeService(entityManagerFactory);
        EmployeeDto employeeDto = service.getEmployeeById(empId);
        if (employeeDto != null){
            return Response.ok().entity(employeeDto).build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEmployee(EmployeeDto employee){
        service = new EmployeeService(entityManagerFactory);
        try {
            EmployeeDto employeeDto = service.addEmployee(employee);
            if (employeeDto != null) {
                return Response.ok().entity(employeeDto).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        } catch (BadRequestException e) {
            // Handle bad request exception
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteEmployee(@PathParam("id") int empId){
        service = new EmployeeService(entityManagerFactory);
        EmployeeDto employeeDto = service.getEmployeeById(empId);
        if (employeeDto != null){
            service.deleteEmployee(empId);
            return Response.ok().build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
