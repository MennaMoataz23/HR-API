package com.example.persistence.daos;


import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> {
    protected final Class<T> theClass;

    protected AbstractDao(Class<T> clazz) {
        this.theClass = clazz;
    }

    public Optional<T> findOneById(Integer id, EntityManager entityManager) {
        T entity = entityManager.find(theClass, id);
        return Optional.ofNullable(entity);
    }

    public List<T> findAll(EntityManager entityManager) {
        return entityManager.createQuery("FROM " + theClass.getName() + " e", theClass)
                .getResultList();
    }

    public void create(EntityManager entityManager, T entity) {
        entityManager.persist(entity);
    }

    public T update(EntityManager entityManager, T entity) {
        entityManager.merge(entity);
        return entity;
    }

    public void deleteById(EntityManager entityManager, Integer entityId) {
        T entityToRemove = entityManager.find(theClass, entityId);
        if (entityToRemove != null) { // to avoid null pointer exceptions
            entityManager.remove(entityToRemove);
        }
    }

}