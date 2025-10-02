package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.model.User;
import com.bookstore.service.BookService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private BookService bookService;

    @Override
    public void init() {
        bookService = new BookService();
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
                if (!isAdmin(request)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен. Требуются права администратора.");
                    return;
                }
                showNewForm(request, response);
                break;
            case "edit":
                if (!isAdmin(request)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен. Требуются права администратора.");
                    return;
                }
                showEditForm(request, response);
                break;
            case "delete":
                if (!isAdmin(request)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен. Требуются права администратора.");
                    return;
                }
                deleteBook(request, response);
                break;
            default:
                listBooks(request, response);
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
            if (!isAdmin(request)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Доступ запрещен. Требуются права администратора.");
                return;
            }
            saveBook(request, response);
        }
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Book> books = bookService.getAllBooks();
        request.setAttribute("books", books);
        request.getRequestDispatcher("/WEB-INF/views/books/list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/books/form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Optional<Book> book = bookService.getBookById(id);
        
        if (book.isPresent()) {
            request.setAttribute("book", book.get());
            request.getRequestDispatcher("/WEB-INF/views/books/form.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/books");
        }
    }

    private void saveBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idParam = request.getParameter("id");
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String publishing = request.getParameter("publishing");
        String seriya = request.getParameter("seriya");
        String yearParam = request.getParameter("year");
        String priceParam = request.getParameter("price");

        Book book = new Book();
        if (idParam != null && !idParam.isEmpty()) {
            book.setId(Long.parseLong(idParam));
        }
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublishing(publishing);
        book.setSeriya(seriya);
        
        if (yearParam != null && !yearParam.isEmpty()) {
            book.setYear(Integer.parseInt(yearParam));
        }
        
        book.setPrice(new BigDecimal(priceParam));

        bookService.saveBook(book);
        response.sendRedirect(request.getContextPath() + "/books");
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        bookService.deleteBook(id);
        response.sendRedirect(request.getContextPath() + "/books");
    }
    
    /**
     * Проверяет, является ли текущий пользователь администратором
     */
    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User.Role userRole = (User.Role) session.getAttribute("userRole");
            return userRole == User.Role.ADMIN;
        }
        return false;
    }
} 