package com.bookstore.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class CharacterEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Инициализация фильтра
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        // Устанавливаем кодировку UTF-8 для запроса
        httpRequest.setCharacterEncoding("UTF-8");
        
        // Устанавливаем кодировку UTF-8 для ответа
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("text/html; charset=UTF-8");
        
        // Продолжаем выполнение цепочки фильтров
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Очистка ресурсов фильтра
    }
} 