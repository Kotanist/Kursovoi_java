package com.bookstore.repository;

import com.bookstore.model.Sale;
import com.bookstore.util.EMF;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class SaleRepository {

    public List<Sale> findAll() {
        EntityManager em = EMF.getEntityManager();
        try {
            TypedQuery<Sale> query = em.createQuery("SELECT s FROM Sale s ORDER BY s.id", Sale.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<Sale> findById(Long id) {
        EntityManager em = EMF.getEntityManager();
        try {
            Sale sale = em.find(Sale.class, id);
            return Optional.ofNullable(sale);
        } finally {
            em.close();
        }
    }

    public Sale save(Sale sale) {
        EntityManager em = EMF.getEntityManager();
        try {
            em.getTransaction().begin();
            if (sale.getId() == null) {
                em.persist(sale);
            } else {
                sale = em.merge(sale);
            }
            em.getTransaction().commit();
            return sale;
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
            Sale sale = em.find(Sale.class, id);
            if (sale != null) {
                em.remove(sale);
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