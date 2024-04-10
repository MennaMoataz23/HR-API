package com.example.presentation.soap;

import com.example.business.dtos.DepartmentDto;
import com.example.business.services.DepartmentService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

@WebService
public class DepartmentWebService {
    private final DepartmentService service = new DepartmentService();

    @WebMethod
    public List<DepartmentDto> getAllDepartments() {
        return service.getAllDepartments();
    }

    @WebMethod
    public DepartmentDto getDepartment(@WebParam(name = "id") int deptId) {
        return service.getDepartmentById(deptId);
    }

    @WebMethod
    public DepartmentDto createDepartment(@WebParam(name = "department") DepartmentDto department) {
        return service.addDepartment(department);
    }

    @WebMethod
    public void deleteDepartment(@WebParam(name = "id") int deptId) {
        service.deleteDepartment(deptId);
    }

    @WebMethod
    public void updateDepartment(@WebParam(name = "department") DepartmentDto departmentDto) {
        service.updateDepartment(departmentDto);
    }
}
