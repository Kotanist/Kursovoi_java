package com.bookstore.repository;

import com.bookstore.model.User;
import com.bookstore.util.EMF;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class UserRepository {
    
    public User save(User user) {
        EntityManager em = EMF.getEntityManager();
        try {
            em.getTransaction().begin();
            if (user.getId() == null) {
                em.persist(user);
            } else {
                user = em.merge(user);
            }
            em.getTransaction().commit();
            return user;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
    
    public Optional<User> findById(Long id) {
        EntityManager em = EMF.getEntityManager();
        try {
            User user = em.find(User.class, id);
            return Optional.ofNullable(user);
        } finally {
            em.close();
        }
    }
    
    public Optional<User> findByUsername(String username) {
        EntityManager em = EMF.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.username = :username AND u.isActive = true", 
                User.class
            );
            query.setParameter("username", username);
            User user = query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }
    
    public Optional<User> findByEmail(String email) {
        EntityManager em = EMF.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email AND u.isActive = true", 
                User.class
            );
            query.setParameter("email", email);
            User user = query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }
    
    public boolean existsByUsername(String username) {
        EntityManager em = EMF.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.username = :username", 
                Long.class
            );
            query.setParameter("username", username);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
    
    public boolean existsByEmail(String email) {
        EntityManager em = EMF.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.email = :email", 
                Long.class
            );
            query.setParameter("email", email);
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
    
    public List<User> findAll() {
        EntityManager em = EMF.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.isActive = true ORDER BY u.createdAt DESC", 
                User.class
            );
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public void delete(Long id) {
        EntityManager em = EMF.getEntityManager();
        try {
            em.getTransaction().begin();
            User user = em.find(User.class, id);
            if (user != null) {
                // Soft delete - помечаем как неактивного
                user.setActive(false);
                em.merge(user);
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