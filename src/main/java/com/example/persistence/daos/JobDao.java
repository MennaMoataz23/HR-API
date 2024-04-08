package com.example.persistence.daos;


import com.example.business.entities.Job;

public class JobDao extends AbstractDao <Job>{
    private static final JobDao INSTANCE = new JobDao();

    private JobDao() {
        super(Job.class);
    }

    public static JobDao getInstance() {
        return INSTANCE;
    }
}
