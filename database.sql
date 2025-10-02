-- Создание базы данных с кодировкой UTF-8
DROP DATABASE IF EXISTS bookstore_utf8;
CREATE DATABASE bookstore_utf8 
    WITH OWNER = postgres 
    ENCODING = 'UTF8' 
    LC_COLLATE = 'C' 
    LC_CTYPE = 'C' 
    TEMPLATE = template0;

\c bookstore_utf8;

-- Создание таблицы книг
CREATE TABLE IF NOT EXISTS book (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publishing VARCHAR(255),
    seriya VARCHAR(255),
    year INTEGER,
    price NUMERIC(10, 2) NOT NULL
);

-- Создание таблицы продавцов
CREATE TABLE IF NOT EXISTS sale (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    position VARCHAR(100) NOT NULL,
    employment_date DATE,
    date_birth DATE,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Создание таблицы покупателей
CREATE TABLE IF NOT EXISTS customer (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(50) NOT NULL
);

-- Создание таблицы заказов
CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    id_book INTEGER NOT NULL REFERENCES book(id) ON DELETE CASCADE,
    id_customer INTEGER NOT NULL REFERENCES customer(id) ON DELETE CASCADE,
    id_sale INTEGER NOT NULL REFERENCES sale(id) ON DELETE CASCADE,
    date_input DATE NOT NULL,
    date_output DATE,
    quantity INTEGER NOT NULL,
    total_sum NUMERIC(10, 2) NOT NULL
);

-- Создание таблицы пользователей
CREATE TABLE IF NOT EXISTS app_user (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Вставка тестовых данных

-- Книги
INSERT INTO book (title, author, publishing, seriya, year, price) VALUES
('Война и мир', 'Лев Толстой', 'Эксмо', 'Классика', 2020, 899.99),
('Преступление и наказание', 'Федор Достоевский', 'АСТ', 'Классика', 2019, 599.50),
('Мастер и Маргарита', 'Михаил Булгаков', 'Азбука', 'Классика', 2021, 750.00),
('1984', 'Джордж Оруэлл', 'АСТ', 'Современная классика', 2020, 450.00),
('Гарри Поттер и философский камень', 'Дж.К. Роулинг', 'Росмэн', 'Гарри Поттер', 2018, 650.00);

-- Продавцы
INSERT INTO sale (first_name, last_name, position, employment_date, date_birth, email) VALUES
('Иван', 'Петров', 'Старший продавец', '2020-01-15', '1990-05-20', 'ivan.petrov@bookstore.com'),
('Мария', 'Сидорова', 'Продавец', '2021-03-10', '1995-08-15', 'maria.sidorova@bookstore.com'),
('Александр', 'Иванов', 'Консультант', '2019-06-01', '1988-12-03', 'alex.ivanov@bookstore.com');

-- Покупатели
INSERT INTO customer (first_name, last_name, email, phone) VALUES
('Анна', 'Козлова', 'anna.kozlova@mail.ru', '+7-900-123-45-67'),
('Петр', 'Смирнов', 'petr.smirnov@gmail.com', '+7-901-234-56-78'),
('Елена', 'Волкова', 'elena.volkova@yandex.ru', '+7-902-345-67-89'),
('Дмитрий', 'Соколов', 'dmitry.sokolov@mail.ru', '+7-903-456-78-90');

-- Заказы
INSERT INTO orders (id_book, id_customer, id_sale, date_input, date_output, quantity, total_sum) VALUES
(1, 1, 1, '2024-01-15', '2024-01-16', 2, 1799.98),
(2, 2, 2, '2024-01-16', NULL, 1, 599.50),
(3, 3, 1, '2024-01-17', '2024-01-18', 1, 750.00),
(4, 4, 3, '2024-01-18', NULL, 3, 1350.00),
(5, 1, 2, '2024-01-19', '2024-01-20', 1, 650.00); 