package com.bookstore.repository;

import com.bookstore.model.Book;
import com.bookstore.util.EMF;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class BookRepository {

    public List<Book> findAll() {
        EntityManager em = EMF.getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery("SELECT b FROM Book b ORDER BY b.id", Book.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Optional<Book> findById(Long id) {
        EntityManager em = EMF.getEntityManager();
        try {
            Book book = em.find(Book.class, id);
            return Optional.ofNullable(book);
        } finally {
            em.close();
        }
    }

    public Book save(Book book) {
        EntityManager em = EMF.getEntityManager();
        try {
            em.getTransaction().begin();
            if (book.getId() == null) {
                em.persist(book);
            } else {
                book = em.merge(book);
            }
            em.getTransaction().commit();
            return book;
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
            Book book = em.find(Book.class, id);
            if (book != null) {
                em.remove(book);
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

    public List<Book> searchByTitle(String title) {
        EntityManager em = EMF.getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(:title)", Book.class);
            query.setParameter("title", "%" + title + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Book> findByAuthor(String author) {
        EntityManager em = EMF.getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b WHERE LOWER(b.author) LIKE LOWER(:author)", Book.class);
            query.setParameter("author", "%" + author + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
} 