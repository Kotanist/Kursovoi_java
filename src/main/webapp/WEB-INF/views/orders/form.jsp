<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${order != null ? 'Редактировать' : 'Создать'} заказ</title>
    <meta charset="UTF-8">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f5f7fa; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #667eea; margin-bottom: 30px; text-align: center; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; color: #333; font-weight: 500; }
        input[type="number"], input[type="date"], select { width: 100%; padding: 12px; border: 2px solid #e0e0e0; border-radius: 5px; font-size: 14px; transition: border-color 0.3s; }
        input:focus, select:focus { outline: none; border-color: #667eea; }
        .btn { padding: 12px 30px; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; transition: all 0.3s; text-decoration: none; display: inline-block; }
        .btn-primary { background: #667eea; color: white; }
        .btn-primary:hover { background: #5568d3; }
        .btn-secondary { background: #6c757d; color: white; margin-left: 10px; }
        .btn-secondary:hover { background: #5a6268; }
        .form-actions { margin-top: 30px; text-align: center; }
    </style>
</head>
<body>
    <div class="container">
        <h1>${order != null ? '✏️ Редактировать' : '➕ Создать'} заказ</h1>
        
        <form action="${pageContext.request.contextPath}/orders" method="post">
            <input type="hidden" name="action" value="save">
            <c:if test="${order != null}">
                <input type="hidden" name="id" value="${order.id}">
            </c:if>
            
            <div class="form-group">
                <label for="bookId">Книга *</label>
                <select id="bookId" name="bookId" required>
                    <option value="">Выберите книгу</option>
                    <c:forEach var="book" items="${books}">
                        <option value="${book.id}" ${order != null && order.book.id == book.id ? 'selected' : ''}>
                            ${book.title} - ${book.author} (${book.price} ₽)
                        </option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label for="customerId">Покупатель *</label>
                <select id="customerId" name="customerId" required>
                    <option value="">Выберите покупателя</option>
                    <c:forEach var="customer" items="${customers}">
                        <option value="${customer.id}" ${order != null && order.customer.id == customer.id ? 'selected' : ''}>
                            ${customer.fullName} (${customer.email})
                        </option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label for="saleId">Продавец *</label>
                <select id="saleId" name="saleId" required>
                    <option value="">Выберите продавца</option>
                    <c:forEach var="sale" items="${sales}">
                        <option value="${sale.id}" ${order != null && order.sale.id == sale.id ? 'selected' : ''}>
                            ${sale.fullName} (${sale.position})
                        </option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label for="dateInput">Дата заказа *</label>
                <input type="date" id="dateInput" name="dateInput" value="${order.dateInput}" required>
            </div>
            
            <div class="form-group">
                <label for="dateOutput">Дата выдачи</label>
                <input type="date" id="dateOutput" name="dateOutput" value="${order.dateOutput}">
            </div>
            
            <div class="form-group">
                <label for="quantity">Количество *</label>
                <input type="number" id="quantity" name="quantity" value="${order.quantity}" min="1" required>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">💾 Сохранить</button>
                <a href="${pageContext.request.contextPath}/orders" class="btn btn-secondary">❌ Отмена</a>
            </div>
        </form>
    </div>
</body>
</html> 