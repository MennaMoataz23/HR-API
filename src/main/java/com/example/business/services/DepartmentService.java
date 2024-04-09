package com.example.business.services;

import com.example.business.dtos.DepartmentDto;
import com.example.business.dtos.EmployeeDto;
import com.example.business.entities.Department;
import com.example.business.entities.Employee;
import com.example.business.mappers.DepartmentMapper;
import com.example.business.mappers.DepartmentMapperImpl;
import com.example.business.mappers.EmployeeMapper;
import com.example.business.mappers.EmployeeMapperImpl;
import com.example.persistence.Database;
import com.example.persistence.daos.DepartmentDao;
import com.example.persistence.daos.EmployeeDao;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class DepartmentService {
    private final EntityManagerFactory entityManagerFactory;
    private final DepartmentDao departmentDao = DepartmentDao.getInstance();
    private final EmployeeDao employeeDao = EmployeeDao.getInstance();

    public DepartmentService(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<DepartmentDto> getAllDepartments(){
        return Database.doInTransaction(entityManager -> {
            DepartmentMapper mapper = new DepartmentMapperImpl();
            List<Department> departments = departmentDao.findAll(entityManager);
            List<DepartmentDto> departmentDtoList = new ArrayList<>();
            departments.forEach(department -> departmentDtoList.add(mapper.entityToDto(department)));
            return departmentDtoList;
        });
    }

    public DepartmentDto getDepartmentById(int id){
        return Database.doInTransaction(entityManager -> {
            DepartmentMapper mapper = new DepartmentMapperImpl();
            Department department = departmentDao
                    .findOneById(id, entityManager)
                    .orElseThrow(()-> new NoSuchElementException("Department with ID: " + id + " Not Found!"));

            return mapper.entityToDto(department);
        });
    }

    public DepartmentDto addDepartment(DepartmentDto departmentDto){
        return Database.doInTransaction(entityManager -> {
            Employee manager = employeeDao.findOneById(departmentDto.getEmployeeId(), entityManager).orElse(null);
            if (manager != null){
                DepartmentMapper mapper = new DepartmentMapperImpl();
                Department department = mapper.dtoToEntity(departmentDto);
                department.setDepartmentManager(manager);
                departmentDao.create(entityManager,department);
                return departmentDto;
            }else{
                return null;
            }
        });
    }

    public boolean deleteDepartment(int id){
        return Database.doInTransaction(entityManager -> {
            Department department = departmentDao
                    .findOneById(id, entityManager)
                    .orElseThrow(() -> new NoSuchElementException("Department with ID: " + id + " Not Found!"));
                    departmentDao.deleteById(entityManager, id);
                    return true;
        });
    }

    public DepartmentDto updateDepartment(DepartmentDto departmentDto){
        DepartmentMapper mapper = new DepartmentMapperImpl();
        return Database.doInTransaction(entityManager -> {
            if (departmentDto.getId() == null){
                System.out.println("department id cannot be null");
                return null;
            }

            Department existingDepartment = departmentDao
                .findOneById(departmentDto.getId(), entityManager)
                .orElseThrow(() -> new NoSuchElementException("Department with ID: " + departmentDto.getId() + " Not Found!"));

            existingDepartment.setName(departmentDto.getName());
            existingDepartment.setLocation(departmentDto.getLocation());
            Employee manager = employeeDao
                    .findOneById(departmentDto.getEmployeeId(), entityManager)
                    .orElseThrow(() -> new NoSuchElementException("Employee with ID: " + departmentDto.getEmployeeId() + " Not Found!"));

            existingDepartment.setDepartmentManager(manager);
            Department updatedDepartment = departmentDao.update(entityManager, existingDepartment);
            return mapper.entityToDto(updatedDepartment);
        });
    }
}
