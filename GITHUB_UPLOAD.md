# 📤 ИНСТРУКЦИЯ ПО ЗАГРУЗКЕ НА GITHUB

## ✅ ВСЁ ГОТОВО К ЗАГРУЗКЕ!

**Пароль безопасно заменен на placeholder:** `YOUR_PASSWORD_HERE`

---

## 🚀 ШАГИ ДЛЯ ЗАГРУЗКИ:

### 1. После создания репозитория на GitHub

Вы уже на странице создания. После нажатия **"Create repository"** GitHub покажет команды.

### 2. Откройте PowerShell в папке проекта

```powershell
cd C:\Users\Mizu\Desktop\books_java
```

### 3. Выполните команды по порядку:

```bash
# 1. Инициализация Git (если еще не сделали)
git init

# 2. Добавьте все файлы
git add .

# 3. Проверьте что будет закоммичено
git status

# 4. Сделайте первый коммит
git commit -m "Initial commit: Bookstore Management System

Features:
- Java EE web application with MVC architecture
- PostgreSQL database with UTF-8 encoding
- Full authentication and authorization system
- Role-based access control (ADMIN/USER)
- CRUD operations for Book, Customer, Sale, Order
- BCrypt password hashing
- Unit and integration tests with JUnit 5
- Modern responsive UI with JSP/JSTL

Tech Stack:
- Java 17
- Jakarta EE 5.0
- Hibernate 6.5.2
- PostgreSQL
- Maven 3.9
- Jetty 11"

# 5. Переименуйте ветку в main (если нужно)
git branch -M main

# 6. Подключите удаленный репозиторий
git remote add origin https://github.com/Kotanist/Kursovoi_java.git

# 7. Загрузите код
git push -u origin main
```

---

## 📝 ЧТО БУДЕТ ЗАГРУЖЕНО:

### ✅ Безопасные файлы:
- ✅ Весь исходный код Java
- ✅ JSP страницы
- ✅ `persistence.xml` с placeholder паролем `YOUR_PASSWORD_HERE`
- ✅ `database.sql` без паролей
- ✅ `README.md` с полной документацией
- ✅ `ПОЯСНИТЕЛЬНАЯ_ЗАПИСКА.md` для Word
- ✅ `SETUP_INSTRUCTIONS.md` с инструкциями по настройке
- ✅ `.gitignore` со всеми исключениями
- ✅ `pom.xml`
- ✅ Все тесты

### ❌ Что НЕ будет загружено (благодаря .gitignore):
- ❌ Папка `target/` с скомпилированными файлами
- ❌ Папка `.idea/` (настройки IDE)
- ❌ `*.iml` файлы
- ❌ Логи и временные файлы

---

## 🔒 БЕЗОПАСНОСТЬ ПОДТВЕРЖДЕНА:

✅ **Пароль PostgreSQL НЕ в репозитории**
✅ **Используется placeholder: YOUR_PASSWORD_HERE**
✅ **Добавлены инструкции по настройке**
✅ **Преподаватель сможет запустить, следуя SETUP_INSTRUCTIONS.md**

---

## 📋 ПОСЛЕ ЗАГРУЗКИ:

### На странице репозитория будет:

```
Kotanist/Kursovoi_java

📚 Bookstore Management System
Java EE web application for bookstore management with authentication 
and role-based access control.

🔧 Technologies: Java 17, Jakarta EE, Hibernate, PostgreSQL, BCrypt, JUnit 5
🎓 Project Type: University Course Work

Files:
├── src/                          # Исходный код
├── README.md                     # Документация
├── SETUP_INSTRUCTIONS.md         # Инструкции по настройке
├── ПОЯСНИТЕЛЬНАЯ_ЗАПИСКА.md      # Для Word документа
├── database.sql                  # SQL схема
├── pom.xml                       # Maven конфигурация
└── .gitignore                    # Исключения
```

---

## 🎓 ДЛЯ ПРЕПОДАВАТЕЛЯ:

Преподаватель сможет запустить проект:

1. Клонировать: `git clone https://github.com/Kotanist/Kursovoi_java.git`
2. Создать БД: Следовать `SETUP_INSTRUCTIONS.md`
3. Настроить пароль в `persistence.xml`
4. Запустить: `mvn clean jetty:run`
5. Открыть: http://localhost:8081 (admin/admin123)

---

## ✅ ГОТОВО!

После выполнения команд ваш код будет на GitHub!

**Ссылка на репозиторий:**
`https://github.com/Kotanist/Kursovoi_java`

Эту ссылку можно вставить в Word документ и отправить преподавателю! 🎉 