package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.bussiness.entities.*;

import java.time.Instant;

public class App
{
    public static void main( String[] args )
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hrSchema");
        EntityManager entityManager = emf.createEntityManager();
//        truncateTables(entityManager);
        addData(entityManager);

        entityManager.close();
    }

    private static void truncateTables(EntityManager entityManager) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // Truncate tables first to avoid duplication
        entityManager.createNativeQuery("DELETE FROM job").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM employee").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM department").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM salary").executeUpdate();
        entityManager.createNativeQuery("DELETE FROM attendance").executeUpdate();

        transaction.commit();
    }
    private static void addData(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        // Adding jobs
        Job job1 = new Job();
        job1.setTitle("Software engineer");

        Job job2 = new Job();
        job2.setTitle("Graphic designer");

        Job job3 = new Job();
        job3.setTitle("Consultant");

        entityManager.persist(job1);
        entityManager.persist(job2);
        entityManager.persist(job3);

        // Adding departments
        Department department1 = new Department();
        department1.setName("Engineering");
        department1.setLocation("Headquarters");

        Department department2 = new Department();
        department2.setName("Design");
        department2.setLocation("Headquarters");

        entityManager.persist(department1);
        entityManager.persist(department2);

        // Adding employees
        Employee employee1 = new Employee();
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setEmail("john.doe@example.com");
        employee1.setPhoneNumber("+1234567890");
        employee1.setHireDate(Instant.now());
        employee1.setJob(job1); // Assigning job
        employee1.setDepartment(department1); // Assigning department

        Employee employee2 = new Employee();
        employee2.setFirstName("Jane");
        employee2.setLastName("Smith");
        employee2.setEmail("jane.smith@example.com");
        employee2.setPhoneNumber("+1987654321");
        employee2.setHireDate(Instant.now());
        employee2.setJob(job2); // Assigning job
        employee2.setDepartment(department2); // Assigning department

        department1.setDepartmentManager(employee1);
        department2.setDepartmentManager(employee1);

        entityManager.persist(employee1);
        entityManager.persist(employee2);

        // Adding attendance
        Attendance attendance1 = new Attendance();
        attendance1.setEmployee(employee1);
        attendance1.setDate(Instant.now());
        attendance1.setStatus(EmployeeStatus.ABSENT.name());

        Attendance attendance2 = new Attendance();
        attendance2.setEmployee(employee2);
        attendance2.setDate(Instant.now());
        attendance2.setStatus(EmployeeStatus.PRESENT.name());

        entityManager.persist(attendance1);
        entityManager.persist(attendance2);

        // Adding salaries
        Salary salary1 = new Salary();
        salary1.setEmployee(employee1);
        salary1.setAmount(5000.00);
        salary1.setStartDate(Instant.now());
        // Set an end date if necessary

        Salary salary2 = new Salary();
        salary2.setEmployee(employee2);
        salary2.setAmount(4500.00);
        salary2.setStartDate(Instant.now());
        // Set an end date if necessary

        entityManager.persist(salary1);
        entityManager.persist(salary2);

        // Adding more jobs
        Job job4 = new Job();
        job4.setTitle("Project Manager");

        entityManager.persist(job4);

        // Adding more departments
        Department department3 = new Department();
        department3.setName("Human Resources");
        department3.setLocation("Headquarters");

        entityManager.persist(department3);

        // Adding more employees
        Employee employee3 = new Employee();
        employee3.setFirstName("Alice");
        employee3.setLastName("Johnson");
        employee3.setEmail("alice.johnson@example.com");
        employee3.setPhoneNumber("+1122334455");
        employee3.setHireDate(Instant.now());
        employee3.setJob(job4); // Assigning job
        employee3.setDepartment(department3); // Assigning department

        entityManager.persist(employee3);

        // Adding more attendance
        Attendance attendance3 = new Attendance();
        attendance3.setEmployee(employee3);
        attendance3.setDate(Instant.now());
        attendance3.setStatus(EmployeeStatus.LATE.name());

        entityManager.persist(attendance3);

        // Adding more salaries
        Salary salary3 = new Salary();
        salary3.setEmployee(employee3);
        salary3.setAmount(6000.00);
        salary3.setStartDate(Instant.now());
        // Set an end date if necessary

        entityManager.persist(salary3);

        entityManager.getTransaction().commit();
    }

}
