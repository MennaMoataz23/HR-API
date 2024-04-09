package com.example.presentation.controllers;

import com.example.business.dtos.DepartmentDto;
import com.example.business.services.DepartmentService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("departments")
public class DepartmentController {
    EntityManagerFactory entityManagerFactory;
    DepartmentService service;

    @GET
    @Path("{id}")
    public Response getDepartment(@PathParam("id") int deptId){
        service = new DepartmentService(entityManagerFactory);
        DepartmentDto departmentDto = service.getDepartmentById(deptId);
        if (departmentDto != null){
            return Response.ok().entity(departmentDto).build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDepartment(DepartmentDto department){
        service = new DepartmentService(entityManagerFactory);
        DepartmentDto departmentDto = service.addDepartment(department);
        if (departmentDto != null){
            return Response.ok().entity(departmentDto).build();
        }else{
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteDepartment(@PathParam("id") int deptId){
        service = new DepartmentService(entityManagerFactory);
        DepartmentDto departmentDto = service.getDepartmentById(deptId);
        if (departmentDto != null){
            service.deleteDepartment(deptId);
            return Response.ok().build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDepartment(@PathParam("id") Integer deptId, DepartmentDto departmentDto){
        service = new DepartmentService(entityManagerFactory);
        System.out.println("1 " + departmentDto);
        DepartmentDto existingDepartment = service.getDepartmentById(deptId);
        if (existingDepartment != null){
            if (departmentDto.getId() == null){
                departmentDto.setId(deptId);
            }
            System.out.println("2 " + existingDepartment);
            service.updateDepartment(departmentDto);
            System.out.println("3 " + existingDepartment);
            return Response.ok().entity(departmentDto).build();
        }else {
            System.out.println("existingDepartment is null");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
