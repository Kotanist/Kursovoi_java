package com.bookstore.service;

import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository);
    }

    @Test
    void testGetAllBooks() {
        // Arrange
        Book book1 = new Book("Война и мир", "Лев Толстой", "Эксмо", "Классика", 2020, new BigDecimal("899.99"));
        Book book2 = new Book("1984", "Джордж Оруэлл", "АСТ", null, 2020, new BigDecimal("450.00"));
        List<Book> expectedBooks = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(expectedBooks);

        // Act
        List<Book> actualBooks = bookService.getAllBooks();

        // Assert
        assertEquals(2, actualBooks.size());
        assertEquals(expectedBooks, actualBooks);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBookById() {
        // Arrange
        Book book = new Book("Война и мир", "Лев Толстой", "Эксмо", "Классика", 2020, new BigDecimal("899.99"));
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        Optional<Book> result = bookService.getBookById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Война и мир", result.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveBook_Success() {
        // Arrange
        Book book = new Book("Война и мир", "Лев Толстой", "Эксмо", "Классика", 2020, new BigDecimal("899.99"));

        when(bookRepository.save(book)).thenReturn(book);

        // Act
        Book savedBook = bookService.saveBook(book);

        // Assert
        assertNotNull(savedBook);
        assertEquals("Война и мир", savedBook.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testSaveBook_EmptyTitle_ThrowsException() {
        // Arrange
        Book book = new Book("", "Лев Толстой", "Эксмо", "Классика", 2020, new BigDecimal("899.99"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookService.saveBook(book));
        verify(bookRepository, never()).save(any());
    }

    @Test
    void testSaveBook_NullAuthor_ThrowsException() {
        // Arrange
        Book book = new Book("Война и мир", null, "Эксмо", "Классика", 2020, new BigDecimal("899.99"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookService.saveBook(book));
        verify(bookRepository, never()).save(any());
    }

    @Test
    void testSaveBook_InvalidPrice_ThrowsException() {
        // Arrange
        Book book = new Book("Война и мир", "Лев Толстой", "Эксмо", "Классика", 2020, new BigDecimal("-10.00"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> bookService.saveBook(book));
        verify(bookRepository, never()).save(any());
    }

    @Test
    void testDeleteBook() {
        // Arrange
        Long bookId = 1L;
        doNothing().when(bookRepository).delete(bookId);

        // Act
        bookService.deleteBook(bookId);

        // Assert
        verify(bookRepository, times(1)).delete(bookId);
    }

    @Test
    void testSearchBooksByTitle() {
        // Arrange
        Book book1 = new Book("Война и мир", "Лев Толстой", "Эксмо", "Классика", 2020, new BigDecimal("899.99"));
        List<Book> expectedBooks = Arrays.asList(book1);

        when(bookRepository.searchByTitle("Война")).thenReturn(expectedBooks);

        // Act
        List<Book> actualBooks = bookService.searchBooksByTitle("Война");

        // Assert
        assertEquals(1, actualBooks.size());
        assertEquals("Война и мир", actualBooks.get(0).getTitle());
        verify(bookRepository, times(1)).searchByTitle("Война");
    }

    @Test
    void testFindBooksByAuthor() {
        // Arrange
        Book book1 = new Book("Война и мир", "Лев Толстой", "Эксмо", "Классика", 2020, new BigDecimal("899.99"));
        Book book2 = new Book("Анна Каренина", "Лев Толстой", "Эксмо", "Классика", 2019, new BigDecimal("799.99"));
        List<Book> expectedBooks = Arrays.asList(book1, book2);

        when(bookRepository.findByAuthor("Толстой")).thenReturn(expectedBooks);

        // Act
        List<Book> actualBooks = bookService.findBooksByAuthor("Толстой");

        // Assert
        assertEquals(2, actualBooks.size());
        verify(bookRepository, times(1)).findByAuthor("Толстой");
    }
} 