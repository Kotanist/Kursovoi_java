package com.bookstore.service;

import com.bookstore.model.Sale;
import com.bookstore.repository.SaleRepository;
import java.util.List;
import java.util.Optional;

public class SaleService {
    private final SaleRepository saleRepository;

    public SaleService() {
        this.saleRepository = new SaleRepository();
    }

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }

    public Sale saveSale(Sale sale) {
        validateSale(sale);
        return saleRepository.save(sale);
    }

    public void deleteSale(Long id) {
        saleRepository.delete(id);
    }

    private void validateSale(Sale sale) {
        if (sale.getFirstName() == null || sale.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (sale.getLastName() == null || sale.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        if (sale.getEmail() == null || !sale.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (sale.getPosition() == null || sale.getPosition().trim().isEmpty()) {
            throw new IllegalArgumentException("Position cannot be empty");
        }
    }
} 