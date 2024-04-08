package com.example.presentation.controllers;

import com.example.business.dtos.EmployeeDto;
//import com.example.business.services.EmployeeService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("employees")
public class EmployeeController {
    static {
        System.out.println("Employee controller");
    }

//    private EmployeeService employeeService;

    @GET
    @Path("{id}")
    public String getEmployees(@PathParam("id") int employeeId){
//        return employeeService.getEmployee(employeeId);
        return "employee id " + employeeId;

    }
}
