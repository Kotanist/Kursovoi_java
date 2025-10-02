<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Регистрация - Книжный магазин</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        
        .auth-container {
            background: white;
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            max-width: 450px;
            width: 100%;
        }
        
        .auth-header {
            text-align: center;
            margin-bottom: 30px;
        }
        
        .auth-header h1 {
            color: #667eea;
            font-size: 2em;
            margin-bottom: 10px;
        }
        
        .auth-header p {
            color: #666;
            font-size: 1.1em;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            margin-bottom: 8px;
            color: #333;
            font-weight: 500;
        }
        
        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 12px 16px;
            border: 2px solid #e1e5e9;
            border-radius: 10px;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        
        input[type="text"]:focus,
        input[type="email"]:focus,
        input[type="password"]:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .btn {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 10px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
        }
        
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 25px rgba(102, 126, 234, 0.4);
        }
        
        .error-message {
            background: #fee;
            color: #c33;
            padding: 12px;
            border-radius: 8px;
            margin-bottom: 20px;
            border: 1px solid #fcc;
        }
        
        .auth-links {
            text-align: center;
            margin-top: 20px;
            padding-top: 20px;
            border-top: 1px solid #eee;
        }
        
        .auth-links a {
            color: #667eea;
            text-decoration: none;
            font-weight: 500;
        }
        
        .auth-links a:hover {
            text-decoration: underline;
        }
        
        .password-help {
            background: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 12px;
            margin-top: 8px;
            font-size: 14px;
            color: #666;
        }
    </style>
    <script>
        function validateForm() {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if (password !== confirmPassword) {
                alert('Пароли не совпадают!');
                return false;
            }
            
            if (password.length < 6) {
                alert('Пароль должен содержать минимум 6 символов!');
                return false;
            }
            
            return true;
        }
    </script>
</head>
<body>
    <div class="auth-container">
        <div class="auth-header">
            <h1>📝 Регистрация</h1>
            <p>Создайте новый аккаунт для работы с системой</p>
        </div>
        
        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                ${errorMessage}
            </div>
        </c:if>
        
        <form method="post" action="${pageContext.request.contextPath}/auth/register" 
              onsubmit="return validateForm()">
            
            <div class="form-group">
                <label for="username">Имя пользователя:</label>
                <input type="text" id="username" name="username" 
                       value="${username}" required minlength="3"
                       placeholder="Минимум 3 символа">
            </div>
            
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" 
                       value="${email}" required
                       placeholder="example@mail.com">
            </div>
            
            <div class="form-group">
                <label for="password">Пароль:</label>
                <input type="password" id="password" name="password" 
                       required minlength="6"
                       placeholder="Минимум 6 символов">
                <div class="password-help">
                    Пароль должен содержать минимум 6 символов
                </div>
            </div>
            
            <div class="form-group">
                <label for="confirmPassword">Подтвердите пароль:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" 
                       required minlength="6"
                       placeholder="Повторите пароль">
            </div>
            
            <button type="submit" class="btn btn-primary">
                Зарегистрироваться
            </button>
        </form>
        
        <div class="auth-links">
            <p>Уже есть аккаунт? 
                <a href="${pageContext.request.contextPath}/auth/login">
                    Войти в систему
                </a>
            </p>
        </div>
    </div>
</body>
</html> 