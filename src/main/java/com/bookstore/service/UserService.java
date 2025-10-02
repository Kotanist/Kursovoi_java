package com.bookstore.service;

import com.bookstore.model.User;
import com.bookstore.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

public class UserService {
    
    private UserRepository userRepository;
    
    public UserService() {
        this.userRepository = new UserRepository();
    }
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Регистрация нового пользователя
     */
    public User register(String username, String email, String password, User.Role role) {
        // Проверяем, что пользователь не существует
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует");
        }
        
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }
        
        // Валидация данных
        validateUserData(username, email, password);
        
        // Создаем пользователя с хешированным паролем
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(hashPassword(password));
        user.setRole(role != null ? role : User.Role.USER);
        user.setActive(true);
        
        return userRepository.save(user);
    }
    
    /**
     * Аутентификация пользователя
     */
    public Optional<User> authenticate(String username, String password) {
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            return Optional.empty();
        }
        
        Optional<User> userOpt = userRepository.findByUsername(username.trim());
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.isActive() && checkPassword(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        
        return Optional.empty();
    }
    
    /**
     * Поиск пользователя по ID
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    /**
     * Поиск пользователя по имени пользователя
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    /**
     * Получить всех пользователей
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    /**
     * Обновление пользователя
     */
    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("ID пользователя не может быть null");
        }
        
        return userRepository.save(user);
    }
    
    /**
     * Изменение пароля пользователя
     */
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
        Optional<User> userOpt = userRepository.findById(userId);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            // Проверяем старый пароль
            if (checkPassword(oldPassword, user.getPassword())) {
                // Валидируем новый пароль
                if (newPassword == null || newPassword.trim().length() < 6) {
                    throw new IllegalArgumentException("Пароль должен содержать минимум 6 символов");
                }
                
                // Устанавливаем новый пароль
                user.setPassword(hashPassword(newPassword));
                userRepository.save(user);
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Деактивация пользователя
     */
    public void deactivateUser(Long userId) {
        userRepository.delete(userId);
    }
    
    /**
     * Создание администратора по умолчанию
     */
    public void createDefaultAdminIfNotExists() {
        if (userRepository.findAll().isEmpty()) {
            register("admin", "admin@bookstore.com", "admin123", User.Role.ADMIN);
        }
    }
    
    // Приватные методы для работы с паролями
    
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    
    private boolean checkPassword(String password, String hashedPassword) {
        try {
            return BCrypt.checkpw(password, hashedPassword);
        } catch (Exception e) {
            return false;
        }
    }
    
    private void validateUserData(String username, String email, String password) {
        if (username == null || username.trim().length() < 3) {
            throw new IllegalArgumentException("Имя пользователя должно содержать минимум 3 символа");
        }
        
        if (email == null || !isValidEmail(email)) {
            throw new IllegalArgumentException("Некорректный email адрес");
        }
        
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Пароль должен содержать минимум 6 символов");
        }
    }
    
    private boolean isValidEmail(String email) {
        return email != null && 
               email.contains("@") && 
               email.contains(".") && 
               email.length() > 5;
    }
} 