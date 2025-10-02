package com.bookstore.controller;

import com.bookstore.model.User;
import com.bookstore.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {
    
    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        
        switch (pathInfo) {
            case "/login":
                showLoginPage(request, response);
                break;
            case "/register":
                showRegisterPage(request, response);
                break;
            case "/logout":
                logout(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/auth/login");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }
        
        switch (pathInfo) {
            case "/login":
                processLogin(request, response);
                break;
            case "/register":
                processRegister(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/auth/login");
                break;
        }
    }

    private void showLoginPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Если пользователь уже авторизован, перенаправляем на главную
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("currentUser") != null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    private void showRegisterPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Если пользователь уже авторизован, перенаправляем на главную
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("currentUser") != null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
    }

    private void processLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
            Optional<User> userOpt = userService.authenticate(username, password);
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                
                // Создаем сессию для пользователя
                HttpSession session = request.getSession(true);
                session.setAttribute("currentUser", user);
                session.setAttribute("username", user.getUsername());
                session.setAttribute("userRole", user.getRole());
                
                // Устанавливаем время жизни сессии (30 минут)
                session.setMaxInactiveInterval(30 * 60);
                
                // Перенаправляем на главную страницу
                response.sendRedirect(request.getContextPath() + "/");
            } else {
                // Ошибка аутентификации
                request.setAttribute("errorMessage", "Неверное имя пользователя или пароль");
                request.setAttribute("username", username); // Сохраняем введенное имя пользователя
                request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка при входе в систему: " + e.getMessage());
            request.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        }
    }

    private void processRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        // Сохраняем введенные данные для восстановления при ошибке
        request.setAttribute("username", username);
        request.setAttribute("email", email);
        
        try {
            // Проверяем, что пароли совпадают
            if (password == null || !password.equals(confirmPassword)) {
                throw new IllegalArgumentException("Пароли не совпадают");
            }
            
            // Регистрируем пользователя
            User newUser = userService.register(username, email, password, User.Role.USER);
            
            // Автоматически авторизуем пользователя после регистрации
            HttpSession session = request.getSession(true);
            session.setAttribute("currentUser", newUser);
            session.setAttribute("username", newUser.getUsername());
            session.setAttribute("userRole", newUser.getRole());
            session.setMaxInactiveInterval(30 * 60);
            
            // Устанавливаем сообщение об успехе
            session.setAttribute("successMessage", 
                "Добро пожаловать, " + newUser.getUsername() + "! Вы успешно зарегистрированы.");
            
            // Перенаправляем на главную страницу
            response.sendRedirect(request.getContextPath() + "/");
            
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Ошибка при регистрации: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
        }
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        // Удаляем сессию пользователя
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // Перенаправляем на страницу входа с сообщением
        response.sendRedirect(request.getContextPath() + "/auth/login?message=logged_out");
    }
} 