package com.example.business.services;

import com.example.business.dtos.AttendanceDto;
import com.example.business.dtos.DepartmentDto;
import com.example.business.dtos.EmployeeDto;
import com.example.business.entities.Attendance;
import com.example.business.entities.Department;
import com.example.business.entities.Employee;
import com.example.business.mappers.*;
import com.example.persistence.Database;
import com.example.persistence.daos.AttendanceDao;
import com.example.persistence.daos.DepartmentDao;
import com.example.persistence.daos.EmployeeDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class AttendanceService {
    private final EntityManagerFactory entityManagerFactory;
    private final AttendanceDao attendanceDao = AttendanceDao.getInstance();
    private final EmployeeDao employeeDao = EmployeeDao.getInstance();

    public AttendanceService (EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<AttendanceDto> getAllAttendance(){
        return Database.doInTransaction(entityManager -> {
            AttendanceMapper mapper = new AttendanceMapperImpl();
            List<Attendance> attendances = attendanceDao.findAll(entityManager);
            List<AttendanceDto> attendanceDtoList = new ArrayList<>();
            attendances.forEach(attendance -> attendanceDtoList.add(mapper.entityToDto(attendance)));
            return attendanceDtoList;
        });
    }

    public AttendanceDto getAttendanceById(int id){
        return Database.doInTransaction(entityManager -> {
            AttendanceMapper mapper = new AttendanceMapperImpl();
            Attendance attendance = attendanceDao
                    .findOneById(id, entityManager)
                    .orElseThrow(()-> new NoSuchElementException("Attendance with ID: " + id +" Not Found"));
            return mapper.entityToDto(attendance);
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
            Attendance attendance = attendanceDao
                    .findOneById(id, entityManager)
                    .orElseThrow(()-> new NoSuchElementException("Attendance with ID: " + id +" Not Found"));

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

    public AttendanceDto updateAttendance(AttendanceDto attendanceDto){
        AttendanceMapper mapper = new AttendanceMapperImpl();
        return Database.doInTransaction(entityManager -> {
            if (attendanceDto.getId() == null){
                System.out.println("attendance id cannot be null");
                return null;
            }

            Attendance existingAttendance = attendanceDao
                .findOneById(attendanceDto.getId(), entityManager)
                .orElseThrow(()-> new NoSuchElementException("Attendance with ID: " + attendanceDto.getId() +" Not Found"));

            existingAttendance.setDate(attendanceDto.getDate());
            existingAttendance.setStatus(attendanceDto.getStatus());
            Employee employee = employeeDao.
                    findOneById(attendanceDto.getEmployeeId(), entityManager)
                    .orElseThrow(()-> new NoSuchElementException("Employee with this id " + attendanceDto.getEmployeeId() + " Not found"));

            existingAttendance.setEmployee(employee);
            Attendance updatedAttendance = attendanceDao.update(entityManager, existingAttendance);
            return mapper.entityToDto(updatedAttendance);
        });
    }
}
