package com.example.business.services;


import com.example.business.dtos.EmployeeDto;
import com.example.business.entities.Department;
import com.example.business.entities.Employee;
import com.example.business.entities.Job;
import com.example.business.mappers.EmployeeMapper;
import com.example.business.mappers.EmployeeMapperImpl;
import com.example.persistence.Database;
import com.example.persistence.daos.DepartmentDao;
import com.example.persistence.daos.EmployeeDao;
import com.example.persistence.daos.JobDao;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class EmployeeService {
    private final EmployeeDao employeeDao = EmployeeDao.getInstance();
    private final JobDao jobDao = JobDao.getInstance();
    private final DepartmentDao departmentDao = DepartmentDao.getInstance();


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
            Employee employee = employeeDao
                    .findOneById(id, entityManager)
                    .orElseThrow(()-> new NoSuchElementException("Employee with ID: " + id +" Not Found"));
            if (employee != null){
                return mapper.entityToDto(employee);
            }else{
                return null;
            }
        });
    }


    public EmployeeDto addEmployee(EmployeeDto employeeDto){
        return Database.doInTransaction(entityManager -> {
            Employee manager = null;
            Department department = null;
            Integer jobId = employeeDto.getJobId();
            if (jobId == null) {
                throw new BadRequestException("JobId should be provided");
            }

            Job job = jobDao
                    .findOneById(jobId, entityManager)
                    .orElseThrow(()-> new NoSuchElementException("Job with ID: " + employeeDto.getJobId() +" Not Found"));

            if (employeeDto.getDepartmentId() != null){
                department = departmentDao
                        .findOneById(employeeDto.getDepartmentId(), entityManager)
                        .orElseThrow(()-> new NoSuchElementException("Department with ID: " + employeeDto.getDepartmentId() +" Not Found"));
            }

            if (employeeDto.getManagerId() != null){
                manager = employeeDao
                        .findOneById(employeeDto.getManagerId(), entityManager)
                        .orElseThrow(()-> new NoSuchElementException("Manager with ID: " + employeeDto.getManagerId() +" Not Found"));
            }

            if (employeeDto.getFirstName() == null || employeeDto.getLastName() == null){
                throw new NoSuchElementException("First name and last name should be provided");
            }

            if (employeeDto.getJobId() == null){
                throw new NoSuchElementException("Job should be provided");
            }

            EmployeeMapper mapper = new EmployeeMapperImpl();
            Employee employee = mapper.dtoToEntity(employeeDto);

            employee.setJob(job);
            employee.setDepartment(department);
            employee.setManager(manager);
            employeeDao.create(entityManager, employee);
            employeeDto.setId(employee.getId());
//            employeeDto.setJobId(jobId);
            return mapper.entityToDto(employee);
        });
    }

    public boolean deleteEmployee(int id){
        return Database.doInTransaction(entityManager -> {
            Employee employee = employeeDao
                .findOneById(id, entityManager)
                .orElseThrow(()-> new NoSuchElementException("Employee with ID: " + id +" Not Found"));

            employeeDao.deleteById(entityManager, id);
            return true;
        });
    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto){
        EmployeeMapper mapper = new EmployeeMapperImpl();
        System.out.println("update employee service");
        return Database.doInTransaction(entityManager -> {
            Employee manager = null;
            Department department = null;

            if (employeeDto.getJobId() == null) {
                throw new BadRequestException("JobId should be provided");
            }

            Employee existingEmployee = employeeDao
                    .findOneById(employeeDto.getId(), entityManager)
                    .orElseThrow(()-> new NoSuchElementException("Employee with ID: " + employeeDto.getId() +" Not Found"));

            Job job = jobDao
                    .findOneById(employeeDto.getJobId(), entityManager)
                    .orElseThrow(()-> new NoSuchElementException("Job with ID: " + employeeDto.getJobId() + " Not Found"));

            if (employeeDto.getFirstName() == null || employeeDto.getLastName() == null){
                throw new NoSuchElementException("First name and last name should be provided");
            }

            if (employeeDto.getDepartmentId() != null){
                department = departmentDao
                        .findOneById(employeeDto.getDepartmentId(), entityManager)
                        .orElseThrow(()-> new NoSuchElementException("Department with ID: " + employeeDto.getDepartmentId() +" Not Found"));
            }

            if (employeeDto.getManagerId() != null){
                manager = employeeDao
                        .findOneById(employeeDto.getManagerId(), entityManager)
                        .orElseThrow(()-> new NoSuchElementException("Manager with ID: " + employeeDto.getManagerId() +" Not Found"));
            }

            existingEmployee.setFirstName(employeeDto.getFirstName());
            existingEmployee.setLastName(employeeDto.getLastName());
            existingEmployee.setEmail(employeeDto.getEmail());
            existingEmployee.setPhoneNumber(employeeDto.getPhoneNumber());
            existingEmployee.setSalary(employeeDto.getSalary());
            existingEmployee.setHireDate(employeeDto.getHireDate());
            existingEmployee.setJob(job);
            existingEmployee.setManager(manager);
            existingEmployee.setDepartment(department);

            Employee updatedEmployee = employeeDao.update(entityManager, existingEmployee);
            return mapper.entityToDto(updatedEmployee);
        });
    }

}
