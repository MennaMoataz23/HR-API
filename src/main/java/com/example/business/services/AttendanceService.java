package com.example.business.services;

import com.example.business.dtos.AttendanceDto;
import com.example.business.dtos.DepartmentDto;
import com.example.business.entities.Attendance;
import com.example.business.entities.Department;
import com.example.business.entities.Employee;
import com.example.business.mappers.AttendanceMapper;
import com.example.business.mappers.AttendanceMapperImpl;
import com.example.business.mappers.DepartmentMapper;
import com.example.business.mappers.DepartmentMapperImpl;
import com.example.persistence.Database;
import com.example.persistence.daos.AttendanceDao;
import com.example.persistence.daos.DepartmentDao;
import com.example.persistence.daos.EmployeeDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class AttendanceService {
    private final EntityManagerFactory entityManagerFactory;
    private final AttendanceDao attendanceDao = AttendanceDao.getInstance();
    private final EmployeeDao employeeDao = EmployeeDao.getInstance();

    public AttendanceService (EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }

    public AttendanceDto getAttendanceById(int id){
        return Database.doInTransaction(entityManager -> {
            AttendanceMapper mapper = new AttendanceMapperImpl();
            Attendance attendance = attendanceDao.findOneById(id, entityManager).orElse(null);
            if (attendance != null){
                return mapper.entityToDto(attendance);
            }else{
                return null;
            }
        });
    }

    public AttendanceDto addAttendance(AttendanceDto attendanceDto){
        return Database.doInTransaction(entityManager -> {
            Employee employee = employeeDao.findOneById(attendanceDto.getEmployeeId(), entityManager).orElse(null);
            if (employee != null){
                AttendanceMapper mapper = new AttendanceMapperImpl();
                Attendance attendance = mapper.dtoToEntity(attendanceDto);
                attendance.setEmployee(employee);
                attendanceDao.create(entityManager, attendance);
                return attendanceDto;
            }else{
                return null;
            }
        });
    }

    public boolean deleteAttendance(int id){
        return Database.doInTransaction(entityManager -> {
            Attendance attendance = attendanceDao.findOneById(id, entityManager).orElse(null);
            if (attendance != null){
                try{
                    attendanceDao.deleteById(entityManager, id);
                    return true;
                }catch (Exception e){
                    System.out.println("attendance deletion failed!");
                    e.printStackTrace();
                }
            }
            return false;
        });
    }
}
