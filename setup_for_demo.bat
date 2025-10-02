@echo off
chcp 65001 >nul
echo ========================================
echo 📚 НАСТРОЙКА BOOKSTORE MANAGEMENT SYSTEM
echo ========================================
echo.

:: Запросить пароль PostgreSQL
set /p PGPASS="Введите пароль PostgreSQL (например: postgres): "
echo.

echo 📋 Шаг 1/3: Создание базы данных и загрузка данных...
set PGPASSWORD=%PGPASS%
psql -U postgres -f database.sql 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo ❌ Ошибка! Проверьте:
    echo    - PostgreSQL установлен и запущен
    echo    - Пароль правильный
    echo    - psql доступен в PATH
    pause
    exit /b 1
)
echo ✅ База данных создана и заполнена!
echo.

echo 📋 Шаг 2/3: Проверка данных...
psql -U postgres -d bookstore_utf8 -c "SELECT 'books' as table_name, COUNT(*) FROM book UNION ALL SELECT 'customers', COUNT(*) FROM customer UNION ALL SELECT 'sales', COUNT(*) FROM sale UNION ALL SELECT 'orders', COUNT(*) FROM orders;" 2>nul
echo.

echo 📋 Шаг 3/3: Настройка подключения...
echo ⚠️  ВАЖНО: Откройте файл:
echo    src\main\resources\META-INF\persistence.xml
echo.
echo    Найдите строку 20:
echo    ^<property name="jakarta.persistence.jdbc.password" value="furia0324"/^>
echo.
echo    Замените на:
echo    ^<property name="jakarta.persistence.jdbc.password" value="%PGPASS%"/^>
echo.

set /p READY="Вы изменили пароль в persistence.xml? (y/n): "
if /i not "%READY%"=="y" (
    echo.
    echo ⏸️  Измените пароль в persistence.xml и запустите скрипт снова!
    pause
    exit /b 0
)

echo.
echo ========================================
echo ✅ ВСЁ ГОТОВО!
echo ========================================
echo.
echo 🚀 Теперь запустите приложение:
echo    mvn jetty:run
echo.
echo 🌐 Затем откройте браузер:
echo    http://localhost:8081
echo.
echo 🔐 Войдите как администратор:
echo    Логин: admin
echo    Пароль: admin123
echo.
pause 