package com.bookstore.controller;

import com.bookstore.model.Sale;
import com.bookstore.service.SaleService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@WebServlet("/sales")
public class SaleServlet extends HttpServlet {
    private SaleService saleService;

    @Override
    public void init() {
        saleService = new SaleService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteSale(request, response);
                break;
            default:
                listSales(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        
        if ("save".equals(action)) {
            saveSale(request, response);
        }
    }

    private void listSales(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Sale> sales = saleService.getAllSales();
        request.setAttribute("sales", sales);
        request.getRequestDispatcher("/WEB-INF/views/sales/list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/sales/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Optional<Sale> sale = saleService.getSaleById(id);
        
        if (sale.isPresent()) {
            request.setAttribute("sale", sale.get());
            request.getRequestDispatcher("/WEB-INF/views/sales/form.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/sales");
        }
    }

    private void saveSale(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idParam = request.getParameter("id");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String position = request.getParameter("position");
        String employmentDateParam = request.getParameter("employmentDate");
        String dateBirthParam = request.getParameter("dateBirth");
        String email = request.getParameter("email");

        Sale sale = new Sale();
        if (idParam != null && !idParam.isEmpty()) {
            sale.setId(Long.parseLong(idParam));
        }
        sale.setFirstName(firstName);
        sale.setLastName(lastName);
        sale.setPosition(position);
        
        if (employmentDateParam != null && !employmentDateParam.isEmpty()) {
            sale.setEmploymentDate(LocalDate.parse(employmentDateParam));
        }
        
        if (dateBirthParam != null && !dateBirthParam.isEmpty()) {
            sale.setDateBirth(LocalDate.parse(dateBirthParam));
        }
        
        sale.setEmail(email);

        saleService.saveSale(sale);
        response.sendRedirect(request.getContextPath() + "/sales");
    }

    private void deleteSale(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        saleService.deleteSale(id);
        response.sendRedirect(request.getContextPath() + "/sales");
    }
} 