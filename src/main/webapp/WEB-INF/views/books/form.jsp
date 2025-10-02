<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${book != null ? 'Редактировать' : 'Добавить'} книгу</title>
    <meta charset="UTF-8">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f7fa;
            padding: 20px;
        }
        
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        h1 {
            color: #667eea;
            margin-bottom: 30px;
            text-align: center;
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
        input[type="number"] {
            width: 100%;
            padding: 12px;
            border: 2px solid #e0e0e0;
            border-radius: 5px;
            font-size: 14px;
            transition: border-color 0.3s;
        }
        
        input:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-block;
        }
        
        .btn-primary {
            background: #667eea;
            color: white;
        }
        
        .btn-primary:hover {
            background: #5568d3;
        }
        
        .btn-secondary {
            background: #6c757d;
            color: white;
            margin-left: 10px;
        }
        
        .btn-secondary:hover {
            background: #5a6268;
        }
        
        .form-actions {
            margin-top: 30px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>${book != null ? '✏️ Редактировать' : '➕ Добавить'} книгу</h1>
        
        <form action="${pageContext.request.contextPath}/books" method="post">
            <input type="hidden" name="action" value="save">
            <c:if test="${book != null}">
                <input type="hidden" name="id" value="${book.id}">
            </c:if>
            
            <div class="form-group">
                <label for="title">Название книги *</label>
                <input type="text" id="title" name="title" value="${book.title}" required>
            </div>
            
            <div class="form-group">
                <label for="author">Автор *</label>
                <input type="text" id="author" name="author" value="${book.author}" required>
            </div>
            
            <div class="form-group">
                <label for="publishing">Издательство</label>
                <input type="text" id="publishing" name="publishing" value="${book.publishing}">
            </div>
            
            <div class="form-group">
                <label for="seriya">Серия</label>
                <input type="text" id="seriya" name="seriya" value="${book.seriya}">
            </div>
            
            <div class="form-group">
                <label for="year">Год издания</label>
                <input type="number" id="year" name="year" value="${book.year}" min="1000" max="2100">
            </div>
            
            <div class="form-group">
                <label for="price">Цена *</label>
                <input type="number" id="price" name="price" value="${book.price}" step="0.01" min="0" required>
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-primary">💾 Сохранить</button>
                <a href="${pageContext.request.contextPath}/books" class="btn btn-secondary">❌ Отмена</a>
            </div>
        </form>
    </div>
</body>
</html> 