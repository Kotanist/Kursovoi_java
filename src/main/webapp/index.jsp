<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Книжный магазин - Главная</title>
    <meta charset="UTF-8">
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
        
        .container {
            background: white;
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            max-width: 800px;
            width: 100%;
        }
        
        h1 {
            color: #667eea;
            margin-bottom: 10px;
            font-size: 2.5em;
            text-align: center;
        }
        
        .subtitle {
            text-align: center;
            color: #666;
            margin-bottom: 40px;
            font-size: 1.1em;
        }
        
        .menu-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        
        .menu-item {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            border-radius: 15px;
            text-decoration: none;
            text-align: center;
            transition: transform 0.3s, box-shadow 0.3s;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }
        
        .menu-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 30px rgba(102, 126, 234, 0.4);
        }
        
        .menu-item i {
            font-size: 2.5em;
            margin-bottom: 15px;
        }
        
        .menu-item h2 {
            font-size: 1.3em;
            margin-bottom: 10px;
        }
        
        .menu-item p {
            font-size: 0.9em;
            opacity: 0.9;
        }
        
        .info-box {
            background: #f8f9fa;
            border-left: 4px solid #667eea;
            padding: 20px;
            margin: 30px 0;
            border-radius: 8px;
        }
        
        .info-box h3 {
            color: #667eea;
            margin-bottom: 10px;
        }
        
        .info-box ul {
            list-style: none;
            padding-left: 0;
        }
        
        .info-box li {
            padding: 8px 0;
            color: #555;
        }
        
        .info-box li:before {
            content: "✓ ";
            color: #667eea;
            font-weight: bold;
            margin-right: 8px;
        }
        
        .user-info {
            position: fixed;
            top: 20px;
            right: 20px;
            background: white;
            padding: 15px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            display: flex;
            align-items: center;
            gap: 10px;
            z-index: 1000;
        }
        
        .user-info .username {
            color: #667eea;
            font-weight: bold;
        }
        
        .user-info .role {
            background: #667eea;
            color: white;
            padding: 2px 8px;
            border-radius: 4px;
            font-size: 12px;
            text-transform: uppercase;
        }
        
        .logout-btn {
            background: #dc3545;
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 5px;
            text-decoration: none;
            font-size: 12px;
            cursor: pointer;
            transition: background 0.3s;
        }
        
        .logout-btn:hover {
            background: #c82333;
        }
        
        .success-message {
            background: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <!-- Информация о пользователе -->
    <div class="user-info">
        <span class="username">${sessionScope.username}</span>
        <span class="role">${sessionScope.userRole}</span>
        <a href="${pageContext.request.contextPath}/auth/logout" class="logout-btn"
           onclick="return confirm('Вы уверены, что хотите выйти?')">Выйти</a>
    </div>

    <div class="container">
        <h1>📚 Книжный магазин</h1>
        <p class="subtitle">Информационная система управления</p>
        
        <!-- Сообщение об успешной регистрации -->
        <c:if test="${not empty sessionScope.successMessage}">
            <div class="success-message">
                ${sessionScope.successMessage}
            </div>
            <c:remove var="successMessage" scope="session"/>
        </c:if>
        
        <div class="info-box">
            <h3>Добро пожаловать!</h3>
            <p>Система для управления книжным магазином включает следующие возможности:</p>
            <ul>
                <li>Управление каталогом книг</li>
                <li>Учет покупателей</li>
                <li>Управление персоналом</li>
                <li>Обработка заказов</li>
            </ul>
        </div>
        
        <div class="menu-grid">
            <!-- Книги - доступно всем -->
            <a href="<%= request.getContextPath() %>/books" class="menu-item">
                <div>📖</div>
                <h2>Книги</h2>
                <p>Каталог книг <c:if test="${sessionScope.userRole == 'USER'}">(только просмотр)</c:if></p>
            </a>
            
            <!-- Покупатели - только для администраторов -->
            <c:if test="${sessionScope.userRole == 'ADMIN'}">
                <a href="<%= request.getContextPath() %>/customers" class="menu-item">
                    <div>👥</div>
                    <h2>Покупатели</h2>
                    <p>База покупателей</p>
                </a>
            </c:if>
            
            <!-- Продавцы - только для администраторов -->
            <c:if test="${sessionScope.userRole == 'ADMIN'}">
                <a href="<%= request.getContextPath() %>/sales" class="menu-item">
                    <div>👔</div>
                    <h2>Продавцы</h2>
                    <p>Персонал магазина</p>
                </a>
            </c:if>
            
            <!-- Заказы - только для администраторов -->
            <c:if test="${sessionScope.userRole == 'ADMIN'}">
                <a href="<%= request.getContextPath() %>/orders" class="menu-item">
                    <div>🛒</div>
                    <h2>Заказы</h2>
                    <p>Управление заказами</p>
                </a>
            </c:if>
        </div>
    </div>
</body>
</html> 