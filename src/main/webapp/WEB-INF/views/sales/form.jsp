<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${sale != null ? 'Редактировать' : 'Добавить'} продавца</title>
    <meta charset="UTF-8">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f5f7fa; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; background: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h1 { color: #667eea; margin-bottom: 30px; text-align: center; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; color: #333; font-weight: 500; }
        input[type="text"], input[type="email"], input[type="date"] { width: 100%; padding: 12px; border: 2px solid #e0e0e0; border-radius: 5px; font-size: 14px; transition: border-color 0.3s; }
        input:focus { outline: none; border-color: #667eea; }
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
        <h1>${sale != null ? '✏️ Редактировать' : '➕ Добавить'} продавца</h1>
        
        <form action="${pageContext.request.contextPath}/sales" method="post">
            <input type="hidden" name="action" value="save">
            <c:if test="${sale != null}">
                <input type="hidden" name="id" value="${sale.id}">
            </c:if>
            
            <div class="form-group">
                <label for="firstName">Имя *</label>
                <input type="text" id="firstName" name="firstName" value="${sale.firstName}" required>
            </div>
            
            <div class="form-group">
                <label for="lastName">Фамилия *</label>
                <input type="text" id="lastName" name="lastName" value="${sale.lastName}" required>
            </div>
            
            <div class="form-group">
                <label for="position">Должность *</label>
                <input type="text" id="position" name="position" value="${sale.position}" required>
            </div>
            
            <div class="form-group">
                <label for="employmentDate">Дата приема на работу</label>
                <input type="date" id="employmentDate" name="employmentDate" value="${sale.employmentDate}">
            </div>
            
            <div class="form-group">
                <label for="dateBirth">Дата рождения</label>
                <input type="date" id="dateBirth" name="dateBirth" value="${sale.dateBirth}">
            </div>
            
            <div class="form-group">
                <label for="email">Email *</label>
                <input type="email" id="email" name="email" value="${sale.email}" required>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">💾 Сохранить</button>
                <a href="${pageContext.request.contextPath}/sales" class="btn btn-secondary">❌ Отмена</a>
            </div>
        </form>
    </div>
</body>
</html> 