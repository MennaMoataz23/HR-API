package com.example.persistence.daos;


import com.example.business.entities.Attendance;

public class AttendanceDao extends AbstractDao<Attendance>{
    private static final AttendanceDao INSTANCE = new AttendanceDao();

    private AttendanceDao() {
        super(Attendance.class);
    }

    public static AttendanceDao getInstance() {
        return INSTANCE;
    }
}
