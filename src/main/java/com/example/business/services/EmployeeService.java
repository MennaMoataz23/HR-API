package com.example.business.services;

import com.example.business.dtos.DepartmentDto;
import com.example.business.dtos.EmployeeDto;
import com.example.business.entities.Department;
import com.example.business.entities.Employee;
import com.example.business.entities.Job;
import com.example.business.mappers.DepartmentMapper;
import com.example.business.mappers.DepartmentMapperImpl;
import com.example.business.mappers.EmployeeMapper;
import com.example.business.mappers.EmployeeMapperImpl;
import com.example.persistence.Database;
import com.example.persistence.daos.DepartmentDao;
import com.example.persistence.daos.EmployeeDao;
import com.example.persistence.daos.JobDao;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.BadRequestException;

import java.util.ArrayList;
import java.util.List;


public class EmployeeService {
    private final EntityManagerFactory entityManagerFactory;
    private final EmployeeDao employeeDao = EmployeeDao.getInstance();
    private final JobDao jobDao = JobDao.getInstance();
    private final DepartmentDao departmentDao = DepartmentDao.getInstance();

    public EmployeeService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<EmployeeDto> getAllEmployees(){
        return Database.doInTransaction(entityManager -> {
            EmployeeMapper mapper = new EmployeeMapperImpl();
            List<Employee> employees = employeeDao.findAll(entityManager);
            List<EmployeeDto> employeeDtos = new ArrayList<>();
            employees.forEach(employee -> employeeDtos.add(mapper.entityToDto(employee)));
            return employeeDtos;
        });
    }


    public EmployeeDto getEmployeeById(int id){
        return Database.doInTransaction(entityManager -> {
            EmployeeMapper mapper = new EmployeeMapperImpl();
            Employee employee = employeeDao.findOneById(id, entityManager).orElse(null);
            if (employee != null){
                return mapper.entityToDto(employee);
            }else{
                return null;
            }
        });
    }


    public EmployeeDto addEmployee(EmployeeDto employeeDto){
        return Database.doInTransaction(entityManager -> {
            Integer jobId = employeeDto.getJobId();
            if (jobId == null) {
                throw new BadRequestException("JobId should be provided");
            }

            Job job = jobDao.findOneById(jobId, entityManager).orElse(null);
            Department department = departmentDao.findOneById(employeeDto.getDepartmentId(), entityManager).orElse(null);
            Employee manager = employeeDao.findOneById(employeeDto.getManagerId(), entityManager).orElse(null);
            EmployeeMapper mapper = new EmployeeMapperImpl();
            Employee employee = mapper.dtoToEntity(employeeDto);

            if (job != null) {
                try {
                    employee.setJob(job);
                    employee.setDepartment(department);
                    employee.setManager(manager);
                    employeeDao.create(entityManager, employee);
                    return mapper.entityToDto(employee);
                } catch (Exception e) {
                    System.out.println("Employee creation failed!");
                    e.printStackTrace();
                }
            }
            return null;
        });
    }

    public boolean deleteEmployee(int id){
        return Database.doInTransaction(entityManager -> {
            Employee employee = employeeDao.findOneById(id, entityManager).orElse(null);
            if (employee != null){
                try{
                    employeeDao.deleteById(entityManager, id);
                    return true;
                }catch (Exception e){
                    System.out.println("employee deletion failed!");
                    e.printStackTrace();
                }
            }
            return false;
        });
    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto){
        EmployeeMapper mapper = new EmployeeMapperImpl();
        System.out.println("update employee service");
        return Database.doInTransaction(entityManager -> {
            if (employeeDto.getId() == null){
                System.out.println("employee id cannot be null");
                return null;
            }

            Employee existingEmployee = employeeDao.findOneById(employeeDto.getId(), entityManager).orElse(null);
            if (existingEmployee != null){
                existingEmployee.setFirstName(employeeDto.getFirstName());
                existingEmployee.setLastName(employeeDto.getLastName());
                existingEmployee.setEmail(employeeDto.getEmail());
                existingEmployee.setPhoneNumber(employeeDto.getPhoneNumber());
                existingEmployee.setSalary(employeeDto.getSalary());
                existingEmployee.setHireDate(employeeDto.getHireDate());

                Job job = jobDao.findOneById(employeeDto.getJobId(), entityManager).orElse(null);
                Employee manager = employeeDao.findOneById(employeeDto.getManagerId(), entityManager).orElse(null);
                Department department = departmentDao.findOneById(employeeDto.getDepartmentId(), entityManager).orElse(null);

                if (job == null){
                    throw new RuntimeException("Employee's job cannot be null!");
                }

                existingEmployee.setJob(job);
                existingEmployee.setManager(manager);
                existingEmployee.setDepartment(department);

                Employee updatedEmployee = employeeDao.update(entityManager, existingEmployee);
                return mapper.entityToDto(updatedEmployee);
            }
            return null;
        });
    }

}
