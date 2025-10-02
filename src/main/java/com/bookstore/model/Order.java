package com.bookstore.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_book", nullable = false)
    private Book book;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_customer", nullable = false)
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sale", nullable = false)
    private Sale sale;
    
    @Column(name = "date_input", nullable = false)
    private LocalDate dateInput;
    
    @Column(name = "date_output")
    private LocalDate dateOutput;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(name = "total_sum", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalSum;

    public Order() {
    }

    public Order(Book book, Customer customer, Sale sale, LocalDate dateInput, 
                 LocalDate dateOutput, Integer quantity, BigDecimal totalSum) {
        this.book = book;
        this.customer = customer;
        this.sale = sale;
        this.dateInput = dateInput;
        this.dateOutput = dateOutput;
        this.quantity = quantity;
        this.totalSum = totalSum;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public LocalDate getDateInput() {
        return dateInput;
    }

    public void setDateInput(LocalDate dateInput) {
        this.dateInput = dateInput;
    }

    public LocalDate getDateOutput() {
        return dateOutput;
    }

    public void setDateOutput(LocalDate dateOutput) {
        this.dateOutput = dateOutput;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", book=" + (book != null ? book.getTitle() : "null") +
                ", customer=" + (customer != null ? customer.getFullName() : "null") +
                ", sale=" + (sale != null ? sale.getFullName() : "null") +
                ", dateInput=" + dateInput +
                ", dateOutput=" + dateOutput +
                ", quantity=" + quantity +
                ", totalSum=" + totalSum +
                '}';
    }
} 