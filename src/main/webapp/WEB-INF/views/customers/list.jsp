<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Покупатели - Список</title>
    <meta charset="UTF-8">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f5f7fa; padding: 20px; }
        .container { max-width: 1200px; margin: 0 auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; padding-bottom: 20px; border-bottom: 2px solid #667eea; }
        h1 { color: #667eea; font-size: 2em; }
        .btn { padding: 10px 20px; border: none; border-radius: 5px; text-decoration: none; cursor: pointer; font-size: 14px; transition: all 0.3s; display: inline-block; }
        .btn-primary { background: #667eea; color: white; }
        .btn-primary:hover { background: #5568d3; }
        .btn-secondary { background: #6c757d; color: white; margin-right: 10px; }
        .btn-secondary:hover { background: #5a6268; }
        .btn-success { background: #28a745; color: white; }
        .btn-danger { background: #dc3545; color: white; }
        .btn-danger:hover { background: #c82333; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        thead { background: #667eea; color: white; }
        th, td { padding: 15px; text-align: left; }
        tbody tr { border-bottom: 1px solid #e0e0e0; transition: background 0.2s; }
        tbody tr:hover { background: #f8f9ff; }
        .actions { display: flex; gap: 10px; }
        .empty-state { text-align: center; padding: 60px 20px; color: #999; }
        .empty-state-icon { font-size: 4em; margin-bottom: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>👥 Покупатели</h1>
            <div>
                <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">← Главная</a>
                <a href="${pageContext.request.contextPath}/customers?action=new" class="btn btn-primary">+ Добавить покупателя</a>
            </div>
        </div>
        
        <c:choose>
            <c:when test="${not empty customers}">
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Имя</th>
                            <th>Фамилия</th>
                            <th>Email</th>
                            <th>Телефон</th>
                            <th>Действия</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="customer" items="${customers}">
                            <tr>
                                <td>${customer.id}</td>
                                <td>${customer.firstName}</td>
                                <td>${customer.lastName}</td>
                                <td>${customer.email}</td>
                                <td>${customer.phone}</td>
                                <td>
                                    <div class="actions">
                                        <a href="${pageContext.request.contextPath}/customers?action=edit&id=${customer.id}" class="btn btn-success">Редактировать</a>
                                        <a href="${pageContext.request.contextPath}/customers?action=delete&id=${customer.id}" 
                                           class="btn btn-danger"
                                           onclick="return confirm('Удалить покупателя ${customer.fullName}?')">Удалить</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <div class="empty-state-icon">👥</div>
                    <h2>Нет покупателей</h2>
                    <p>Добавьте первого покупателя</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html> 