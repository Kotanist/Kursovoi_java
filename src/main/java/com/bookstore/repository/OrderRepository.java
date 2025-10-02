package com.bookstore.repository;

import com.bookstore.model.Order;
import com.bookstore.util.EMF;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class OrderRepository {

    public List<Order> findAll() {
        EntityManager em = EMF.getEntityManager();
        try {
            TypedQuery<Order> query = em.createQuery("SELECT o FROM Order o ORDER BY o.dateInput DESC", Order.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<Order> findById(Long id) {
        EntityManager em = EMF.getEntityManager();
        try {
            Order order = em.find(Order.class, id);
            return Optional.ofNullable(order);
        } finally {
            em.close();
        }
    }

    public Order save(Order order) {
        EntityManager em = EMF.getEntityManager();
        try {
            em.getTransaction().begin();
            if (order.getId() == null) {
                em.persist(order);
            } else {
                order = em.merge(order);
            }
            em.getTransaction().commit();
            return order;
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
            Order order = em.find(Order.class, id);
            if (order != null) {
                em.remove(order);
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