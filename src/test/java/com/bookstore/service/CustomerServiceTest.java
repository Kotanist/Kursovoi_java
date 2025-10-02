package com.bookstore.service;

import com.bookstore.model.Customer;
import com.bookstore.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @Test
    void testGetAllCustomers() {
        // Arrange
        Customer customer1 = new Customer("Иван", "Иванов", "ivan@example.com", "+7-900-123-45-67");
        Customer customer2 = new Customer("Петр", "Петров", "petr@example.com", "+7-900-987-65-43");
        List<Customer> expectedCustomers = Arrays.asList(customer1, customer2);

        when(customerRepository.findAll()).thenReturn(expectedCustomers);

        // Act
        List<Customer> actualCustomers = customerService.getAllCustomers();

        // Assert
        assertEquals(2, actualCustomers.size());
        assertEquals(expectedCustomers, actualCustomers);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testGetCustomerById() {
        // Arrange
        Customer customer = new Customer("Иван", "Иванов", "ivan@example.com", "+7-900-123-45-67");
        customer.setId(1L);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        // Act
        Optional<Customer> result = customerService.getCustomerById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Иван", result.get().getFirstName());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveCustomer_Success() {
        // Arrange
        Customer customer = new Customer("Иван", "Иванов", "ivan@example.com", "+7-900-123-45-67");

        when(customerRepository.save(customer)).thenReturn(customer);

        // Act
        Customer savedCustomer = customerService.saveCustomer(customer);

        // Assert
        assertNotNull(savedCustomer);
        assertEquals("Иван", savedCustomer.getFirstName());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testSaveCustomer_EmptyEmail_ThrowsException() {
        // Arrange
        Customer customer = new Customer("Иван", "Иванов", "", "+7-900-123-45-67");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> customerService.saveCustomer(customer));
        verify(customerRepository, never()).save(any());
    }

    @Test
    void testDeleteCustomer() {
        // Arrange
        Long customerId = 1L;
        doNothing().when(customerRepository).delete(customerId);

        // Act
        customerService.deleteCustomer(customerId);

        // Assert
        verify(customerRepository, times(1)).delete(customerId);
    }
} 