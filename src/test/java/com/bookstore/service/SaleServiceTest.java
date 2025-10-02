package com.bookstore.service;

import com.bookstore.model.Sale;
import com.bookstore.repository.SaleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SaleServiceTest {

    @Mock
    private SaleRepository saleRepository;

    private SaleService saleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        saleService = new SaleService(saleRepository);
    }

    @Test
    void testGetAllSales() {
        // Arrange
        Sale sale1 = new Sale("Мария", "Иванова", "Продавец", 
                             LocalDate.of(2020, 1, 15), LocalDate.of(1990, 5, 10), "maria@bookstore.com");
        Sale sale2 = new Sale("Алексей", "Петров", "Старший продавец", 
                             LocalDate.of(2019, 3, 20), LocalDate.of(1985, 8, 22), "alex@bookstore.com");
        List<Sale> expectedSales = Arrays.asList(sale1, sale2);

        when(saleRepository.findAll()).thenReturn(expectedSales);

        // Act
        List<Sale> actualSales = saleService.getAllSales();

        // Assert
        assertEquals(2, actualSales.size());
        assertEquals(expectedSales, actualSales);
        verify(saleRepository, times(1)).findAll();
    }

    @Test
    void testGetSaleById() {
        // Arrange
        Sale sale = new Sale("Мария", "Иванова", "Продавец", 
                           LocalDate.of(2020, 1, 15), LocalDate.of(1990, 5, 10), "maria@bookstore.com");
        sale.setId(1L);

        when(saleRepository.findById(1L)).thenReturn(Optional.of(sale));

        // Act
        Optional<Sale> result = saleService.getSaleById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Мария", result.get().getFirstName());
        verify(saleRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveSale_Success() {
        // Arrange
        Sale sale = new Sale("Мария", "Иванова", "Продавец", 
                           LocalDate.of(2020, 1, 15), LocalDate.of(1990, 5, 10), "maria@bookstore.com");

        when(saleRepository.save(sale)).thenReturn(sale);

        // Act
        Sale savedSale = saleService.saveSale(sale);

        // Assert
        assertNotNull(savedSale);
        assertEquals("Мария", savedSale.getFirstName());
        verify(saleRepository, times(1)).save(sale);
    }

    @Test
    void testDeleteSale() {
        // Arrange
        Long saleId = 1L;
        doNothing().when(saleRepository).delete(saleId);

        // Act
        saleService.deleteSale(saleId);

        // Assert
        verify(saleRepository, times(1)).delete(saleId);
    }
} 