package com.example.presentation.soap;

import com.example.business.dtos.EmployeeDto;
import com.example.business.services.EmployeeService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

@WebService
public class EmployeeWebService {
    private final EmployeeService service = new EmployeeService();

    @WebMethod
    public List<EmployeeDto> getAllEmployees() {
        return service.getAllEmployees();
    }

    @WebMethod
    public EmployeeDto getEmployee(@WebParam(name = "id") int empId) {
        return service.getEmployeeById(empId);
    }

    @WebMethod
    public EmployeeDto createEmployee(@WebParam(name = "employee") EmployeeDto employee) {
        return service.addEmployee(employee);
    }

    @WebMethod
    public void deleteEmployee(@WebParam(name = "id") int empId) {
        service.deleteEmployee(empId);
    }

    @WebMethod
    public void updateEmployee(@WebParam(name = "employee") EmployeeDto employeeDto) {
        service.updateEmployee(employeeDto);
    }
}
