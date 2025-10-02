package com.bookstore.repository;

import com.bookstore.model.Customer;
import com.bookstore.util.EMF;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class CustomerRepository {

    public List<Customer> findAll() {
        EntityManager em = EMF.getEntityManager();
        try {
            TypedQuery<Customer> query = em.createQuery("SELECT c FROM Customer c ORDER BY c.id", Customer.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<Customer> findById(Long id) {
        EntityManager em = EMF.getEntityManager();
        try {
            Customer customer = em.find(Customer.class, id);
            return Optional.ofNullable(customer);
        } finally {
            em.close();
        }
    }

    public Customer save(Customer customer) {
        EntityManager em = EMF.getEntityManager();
        try {
            em.getTransaction().begin();
            if (customer.getId() == null) {
                em.persist(customer);
            } else {
                customer = em.merge(customer);
            }
            em.getTransaction().commit();
            return customer;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = EMF.getEntityManager();
        try {
            em.getTransaction().begin();
            Customer customer = em.find(Customer.class, id);
            if (customer != null) {
                em.remove(customer);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
} 