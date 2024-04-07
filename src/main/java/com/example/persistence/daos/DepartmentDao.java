package com.example.persistence.daos;


import com.example.business.entities.Department;

public class DepartmentDao extends AbstractDao{
    private static final DepartmentDao INSTANCE = new DepartmentDao();

    private DepartmentDao() {
        super(Department.class);
    }

    public static DepartmentDao getInstance() {
        return INSTANCE;
    }
}
