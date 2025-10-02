package com.bookstore.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false)
    private String author;
    
    private String publishing;
    
    private String seriya;
    
    @Column(name = "year")
    private Integer year;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    public Book() {
    }

    public Book(String title, String author, String publishing, String seriya, Integer year, BigDecimal price) {
        this.title = title;
        this.author = author;
        this.publishing = publishing;
        this.seriya = seriya;
        this.year = year;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishing() {
        return publishing;
    }

    public void setPublishing(String publishing) {
        this.publishing = publishing;
    }

    public String getSeriya() {
        return seriya;
    }

    public void setSeriya(String seriya) {
        this.seriya = seriya;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishing='" + publishing + '\'' +
                ", seriya='" + seriya + '\'' +
                ", year=" + year +
                ", price=" + price +
                '}';
    }
} 