package com.bookstore.filter;

import com.bookstore.model.User;
import com.bookstore.service.UserService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    
    private UserService userService;
    
    // Страницы, которые доступны без аутентификации
    private static final List<String> PUBLIC_PATHS = Arrays.asList(
        "/auth/login",
        "/auth/register",
        "/auth/logout"
    );
    
    // Статические ресурсы
    private static final List<String> STATIC_RESOURCES = Arrays.asList(
        ".css", ".js", ".png", ".jpg", ".jpeg", ".gif", ".ico"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.userService = new UserService();
        
        // Создаем администратора по умолчанию при первом запуске
        try {
            userService.createDefaultAdminIfNotExists();
        } catch (Exception e) {
            System.err.println("Ошибка при создании администратора по умолчанию: " + e.getMessage());
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String path = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        
        // Убираем context path из пути
        if (path.startsWith(contextPath)) {
            path = path.substring(contextPath.length());
        }
        
        // Проверяем, является ли запрос статическим ресурсом
        if (isStaticResource(path)) {
            chain.doFilter(request, response);
            return;
        }
        
        // Проверяем, является ли путь публичным
        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }
        
        // Проверяем аутентификацию
        HttpSession session = httpRequest.getSession(false);
        User currentUser = null;
        
        if (session != null) {
            currentUser = (User) session.getAttribute("currentUser");
        }
        
        if (currentUser == null) {
            // Пользователь не аутентифицирован - перенаправляем на страницу входа
            if (isAjaxRequest(httpRequest)) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } else {
                httpResponse.sendRedirect(contextPath + "/auth/login");
                return;
            }
        }
        
        // Проверяем права доступа для административных функций
        if (isAdminPath(path) && !currentUser.isAdmin()) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, 
                "У вас нет прав для доступа к этой странице");
            return;
        }
        
        // Добавляем информацию о пользователе в request
        httpRequest.setAttribute("currentUser", currentUser);
        
        // Пропускаем запрос дальше
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Очистка ресурсов
    }
    
    /**
     * Проверяет, является ли путь публичным (доступным без аутентификации)
     */
    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }
    
    /**
     * Проверяет, является ли запрос статическим ресурсом
     */
    private boolean isStaticResource(String path) {
        return STATIC_RESOURCES.stream().anyMatch(path::endsWith);
    }
    
    /**
     * Проверяет, требует ли путь административных прав
     */
    private boolean isAdminPath(String path) {
        // Административные пути, требующие роли ADMIN
        return path.startsWith("/books") && requiresAdminForBooks(path)
            || path.startsWith("/customers")
            || path.startsWith("/sales")
            || path.startsWith("/orders") && requiresAdminForOrders(path)
            || path.startsWith("/admin/");
    }
    
    /**
     * Проверяет, требует ли операция с книгами прав администратора
     */
    private boolean requiresAdminForBooks(String path) {
        // Обычные пользователи могут только просматривать книги
        String action = extractAction(path);
        return action != null && !action.equals("list") && !action.isEmpty();
    }
    
    /**
     * Проверяет, требует ли операция с заказами прав администратора
     */
    private boolean requiresAdminForOrders(String path) {
        // Пользователи могут создавать и просматривать свои заказы
        // Администраторы могут все
        String action = extractAction(path);
        return action != null && (action.equals("delete") || action.equals("list"));
    }
    
    /**
     * Извлекает действие из параметров запроса
     */
    private String extractAction(String path) {
        if (path.contains("action=")) {
            int start = path.indexOf("action=") + 7;
            int end = path.indexOf("&", start);
            if (end == -1) end = path.length();
            return path.substring(start, end);
        }
        return null;
    }
    
    /**
     * Проверяет, является ли запрос AJAX запросом
     */
    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(requestedWith);
    }
} 