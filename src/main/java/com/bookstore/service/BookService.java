package com.bookstore.service;

import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import java.util.List;
import java.util.Optional;

public class BookService {
    private final BookRepository bookRepository;

    public BookService() {
        this.bookRepository = new BookRepository();
    }

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        validateBook(book);
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }

    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.searchByTitle(title);
    }

    public List<Book> findBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be empty");
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Book author cannot be empty");
        }
        if (book.getPrice() == null || book.getPrice().doubleValue() <= 0) {
            throw new IllegalArgumentException("Book price must be greater than 0");
        }
    }
} 