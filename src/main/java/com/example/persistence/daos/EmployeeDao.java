package com.example.persistence.daos;


import com.example.business.entities.Employee;

public class EmployeeDao extends AbstractDao {
    private static final EmployeeDao INSTANCE = new EmployeeDao();

    private EmployeeDao() {
        super(Employee.class);
    }

    public static EmployeeDao getInstance() {
        return INSTANCE;
    }
}
