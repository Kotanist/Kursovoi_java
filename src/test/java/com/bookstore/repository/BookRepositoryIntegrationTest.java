package com.bookstore.repository;

import com.bookstore.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryIntegrationTest {

    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository = new BookRepository();
    }

    @Test
    void testSaveAndFindBook() {
        // Arrange
        Book book = new Book("Тестовая книга", "Тестовый автор", "Тестовое издательство", 
                           "Тестовая серия", 2023, new BigDecimal("499.99"));

        // Act
        Book savedBook = bookRepository.save(book);
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        // Assert
        assertNotNull(savedBook.getId());
        assertTrue(foundBook.isPresent());
        assertEquals("Тестовая книга", foundBook.get().getTitle());
        assertEquals("Тестовый автор", foundBook.get().getAuthor());
    }

    @Test
    void testFindAllBooks() {
        // Arrange
        Book book1 = new Book("Книга 1", "Автор 1", "Издательство 1", null, 2023, new BigDecimal("299.99"));
        Book book2 = new Book("Книга 2", "Автор 2", "Издательство 2", null, 2023, new BigDecimal("399.99"));
        
        bookRepository.save(book1);
        bookRepository.save(book2);

        // Act
        List<Book> books = bookRepository.findAll();

        // Assert
        assertTrue(books.size() >= 2);
    }

    @Test
    void testSearchByTitle() {
        // Arrange
        Book book = new Book("Уникальная тестовая книга", "Автор", "Издательство", null, 2023, new BigDecimal("199.99"));
        bookRepository.save(book);

        // Act
        List<Book> foundBooks = bookRepository.searchByTitle("Уникальная");

        // Assert
        assertTrue(foundBooks.size() >= 1);
        assertTrue(foundBooks.stream().anyMatch(b -> b.getTitle().contains("Уникальная")));
    }

    @Test
    void testFindByAuthor() {
        // Arrange
        Book book = new Book("Книга автора", "Уникальный Автор", "Издательство", null, 2023, new BigDecimal("299.99"));
        bookRepository.save(book);

        // Act
        List<Book> foundBooks = bookRepository.findByAuthor("Уникальный");

        // Assert
        assertTrue(foundBooks.size() >= 1);
        assertTrue(foundBooks.stream().anyMatch(b -> b.getAuthor().contains("Уникальный")));
    }

    @Test
    void testDeleteBook() {
        // Arrange
        Book book = new Book("Книга для удаления", "Автор", "Издательство", null, 2023, new BigDecimal("199.99"));
        Book savedBook = bookRepository.save(book);

        // Act
        bookRepository.delete(savedBook.getId());
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        // Assert
        assertFalse(foundBook.isPresent());
    }
} 