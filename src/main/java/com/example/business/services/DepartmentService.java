package com.example.business.services;

import com.example.business.dtos.DepartmentDto;
import com.example.business.entities.Department;
import com.example.business.entities.Employee;
import com.example.business.mappers.DepartmentMapper;
import com.example.business.mappers.DepartmentMapperImpl;
import com.example.persistence.Database;
import com.example.persistence.daos.DepartmentDao;
import com.example.persistence.daos.EmployeeDao;
import jakarta.persistence.EntityManagerFactory;

public class DepartmentService {
    private final EntityManagerFactory entityManagerFactory;
    private final DepartmentDao departmentDao = DepartmentDao.getInstance();
    private final EmployeeDao employeeDao = EmployeeDao.getInstance();

    public DepartmentService(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }

    public DepartmentDto getDepartmentById(int id){
        return Database.doInTransaction(entityManager -> {
            DepartmentMapper mapper = new DepartmentMapperImpl();
            Department department = departmentDao.findOneById(id, entityManager).orElse(null);
            if (department != null){
                return mapper.entityToDto(department);
            }else{
                return null;
            }
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
            Department department = departmentDao.findOneById(id, entityManager).orElse(null);
            if (department != null){
                try{
                    departmentDao.deleteById(entityManager, id);
                    return true;
                }catch (Exception e){
                    System.out.println("department deletion failed!");
                    e.printStackTrace();
                }
            }
            return false;
        });
    }

    public void updateDepartment(DepartmentDto departmentDto){
        Database.doInTransactionWithoutResult(entityManager -> {
            if (departmentDto.getId() == null){
                System.out.println("department id cannot be null");
                return;
            }

            Department existingDepartment = departmentDao.findOneById(departmentDto.getId(), entityManager).orElse(null);
            if (existingDepartment != null){
                System.out.println("existingDepartment id: " + existingDepartment.getId());

                existingDepartment.setName(departmentDto.getName());
                existingDepartment.setLocation(departmentDto.getLocation());
                Employee manager = employeeDao.findOneById(departmentDto.getEmployeeId(), entityManager).orElse(null);
                if (manager != null){
                    existingDepartment.setDepartmentManager(manager);
                }
                departmentDao.update(entityManager, existingDepartment);
            }
        });
    }
}
