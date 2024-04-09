package com.example.presentation.controllers;

import com.example.business.dtos.EmployeeDto;
import com.example.business.services.EmployeeService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.BadRequestException;

import java.util.List;

@Path("employees")
public class EmployeeController {
    EmployeeService service = new EmployeeService();

    @GET
    public Response getAllEmployees(){
        List<EmployeeDto> employeeDtos = service.getAllEmployees();
        if (employeeDtos != null){
            return Response.ok().entity(employeeDtos).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @GET
    @Path("{id}")
    public Response getEmployee(@PathParam("id") int empId){
        EmployeeDto employeeDto = service.getEmployeeById(empId);
        return Response.ok().entity(employeeDto).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEmployee(EmployeeDto employee){
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
        EmployeeDto employeeDto = service.getEmployeeById(empId);
        service.deleteEmployee(empId);
        return Response.ok().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmployee(EmployeeDto employeeDto){
        service.updateEmployee(employeeDto);
        return Response.ok().entity(employeeDto).build();
    }

}
