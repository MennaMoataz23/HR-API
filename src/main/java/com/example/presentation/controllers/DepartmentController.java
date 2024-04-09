package com.example.presentation.controllers;

import com.example.business.dtos.DepartmentDto;
import com.example.business.dtos.EmployeeDto;
import com.example.business.services.DepartmentService;
import com.example.business.services.EmployeeService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("departments")
public class DepartmentController {
    EntityManagerFactory entityManagerFactory;
    DepartmentService service;

    @GET
    public Response getAllDepartments(){
        service = new DepartmentService(entityManagerFactory);
        List<DepartmentDto> departmentDtoList = service.getAllDepartments();
        if (departmentDtoList != null){
            return Response.ok().entity(departmentDtoList).build();
        }else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @GET
    @Path("{id}")
    public Response getDepartment(@PathParam("id") int deptId){
        service = new DepartmentService(entityManagerFactory);
        DepartmentDto departmentDto = service.getDepartmentById(deptId);
        return Response.ok().entity(departmentDto).build();
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
        service.deleteDepartment(deptId);
        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDepartment(@PathParam("id") Integer deptId, DepartmentDto departmentDto){
        service = new DepartmentService(entityManagerFactory);
        departmentDto.setId(deptId);
        service.updateDepartment(departmentDto);
        return Response.ok().entity(departmentDto).build();
    }
}
