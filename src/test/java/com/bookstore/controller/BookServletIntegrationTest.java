package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class BookServletIntegrationTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private BookService bookService;

    private BookServlet bookServlet;
    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        bookServlet = new BookServlet();
        
        // Используем рефлексию для внедрения мок-сервиса
        try {
            java.lang.reflect.Field field = BookServlet.class.getDeclaredField("bookService");
            field.setAccessible(true);
            field.set(bookServlet, bookService);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    void testDoGet_ListBooks() throws ServletException, IOException {
        // Arrange
        when(request.getParameter("action")).thenReturn(null);
        when(request.getContextPath()).thenReturn("/bookstore");
        
        Book book1 = new Book("Война и мир", "Лев Толстой", "Эксмо", "Классика", 2020, new BigDecimal("899.99"));
        Book book2 = new Book("1984", "Джордж Оруэлл", "АСТ", null, 2020, new BigDecimal("450.00"));
        List<Book> books = Arrays.asList(book1, book2);
        
        when(bookService.getAllBooks()).thenReturn(books);

        // Act
        bookServlet.doGet(request, response);

        // Assert
        verify(request).setAttribute("books", books);
        verify(request).getRequestDispatcher("/WEB-INF/views/books/list.jsp");
    }

    @Test
    void testDoGet_ShowNewForm() throws ServletException, IOException {
        // Arrange
        when(request.getParameter("action")).thenReturn("new");

        // Act
        bookServlet.doGet(request, response);

        // Assert
        verify(request).getRequestDispatcher("/WEB-INF/views/books/form.jsp");
    }

    @Test
    void testDoGet_ShowEditForm() throws ServletException, IOException {
        // Arrange
        when(request.getParameter("action")).thenReturn("edit");
        when(request.getParameter("id")).thenReturn("1");
        
        Book book = new Book("Война и мир", "Лев Толстой", "Эксмо", "Классика", 2020, new BigDecimal("899.99"));
        book.setId(1L);
        
        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        // Act
        bookServlet.doGet(request, response);

        // Assert
        verify(request).setAttribute("book", book);
        verify(request).getRequestDispatcher("/WEB-INF/views/books/form.jsp");
    }

    @Test
    void testDoGet_DeleteBook() throws ServletException, IOException {
        // Arrange
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn("1");
        when(request.getContextPath()).thenReturn("/bookstore");

        // Act
        bookServlet.doGet(request, response);

        // Assert
        verify(bookService).deleteBook(1L);
        verify(response).sendRedirect("/bookstore/books");
    }

    @Test
    void testDoPost_SaveBook() throws ServletException, IOException {
        // Arrange
        when(request.getParameter("action")).thenReturn("save");
        when(request.getParameter("title")).thenReturn("Война и мир");
        when(request.getParameter("author")).thenReturn("Лев Толстой");
        when(request.getParameter("publishing")).thenReturn("Эксмо");
        when(request.getParameter("seriya")).thenReturn("Классика");
        when(request.getParameter("year")).thenReturn("2020");
        when(request.getParameter("price")).thenReturn("899.99");
        when(request.getContextPath()).thenReturn("/bookstore");

        // Act
        bookServlet.doPost(request, response);

        // Assert
        verify(bookService).saveBook(any(Book.class));
        verify(response).sendRedirect("/bookstore/books");
    }
} 