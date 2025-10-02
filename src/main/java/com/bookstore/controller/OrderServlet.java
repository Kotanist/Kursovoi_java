package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.model.Customer;
import com.bookstore.model.Order;
import com.bookstore.model.Sale;
import com.bookstore.service.BookService;
import com.bookstore.service.CustomerService;
import com.bookstore.service.OrderService;
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

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    private OrderService orderService;
    private BookService bookService;
    private CustomerService customerService;
    private SaleService saleService;

    @Override
    public void init() {
        orderService = new OrderService();
        bookService = new BookService();
        customerService = new CustomerService();
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
                deleteOrder(request, response);
                break;
            default:
                listOrders(request, response);
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
            saveOrder(request, response);
        }
    }

    private void listOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Order> orders = orderService.getAllOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/WEB-INF/views/orders/list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Book> books = bookService.getAllBooks();
        List<Customer> customers = customerService.getAllCustomers();
        List<Sale> sales = saleService.getAllSales();
        
        request.setAttribute("books", books);
        request.setAttribute("customers", customers);
        request.setAttribute("sales", sales);
        
        request.getRequestDispatcher("/WEB-INF/views/orders/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Optional<Order> order = orderService.getOrderById(id);
        
        if (order.isPresent()) {
            List<Book> books = bookService.getAllBooks();
            List<Customer> customers = customerService.getAllCustomers();
            List<Sale> sales = saleService.getAllSales();
            
            request.setAttribute("order", order.get());
            request.setAttribute("books", books);
            request.setAttribute("customers", customers);
            request.setAttribute("sales", sales);
            
            request.getRequestDispatcher("/WEB-INF/views/orders/form.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/orders");
        }
    }

    private void saveOrder(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idParam = request.getParameter("id");
        Long bookId = Long.parseLong(request.getParameter("bookId"));
        Long customerId = Long.parseLong(request.getParameter("customerId"));
        Long saleId = Long.parseLong(request.getParameter("saleId"));
        String dateInputParam = request.getParameter("dateInput");
        String dateOutputParam = request.getParameter("dateOutput");
        Integer quantity = Integer.parseInt(request.getParameter("quantity"));

        Order order = new Order();
        if (idParam != null && !idParam.isEmpty()) {
            order.setId(Long.parseLong(idParam));
        }
        
        Optional<Book> book = bookService.getBookById(bookId);
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        Optional<Sale> sale = saleService.getSaleById(saleId);
        
        if (book.isPresent() && customer.isPresent() && sale.isPresent()) {
            order.setBook(book.get());
            order.setCustomer(customer.get());
            order.setSale(sale.get());
            order.setDateInput(LocalDate.parse(dateInputParam));
            
            if (dateOutputParam != null && !dateOutputParam.isEmpty()) {
                order.setDateOutput(LocalDate.parse(dateOutputParam));
            }
            
            order.setQuantity(quantity);

            orderService.saveOrder(order);
        }
        
        response.sendRedirect(request.getContextPath() + "/orders");
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        orderService.deleteOrder(id);
        response.sendRedirect(request.getContextPath() + "/orders");
    }
} 