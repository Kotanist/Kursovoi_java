@echo off
echo ========================================
echo   Starting Bookstore Application
echo ========================================
echo.
echo Database: PostgreSQL (localhost:5432)
echo Server will start on: http://localhost:8081
echo.
echo Press Ctrl+C to stop the server
echo.

mvn jetty:run