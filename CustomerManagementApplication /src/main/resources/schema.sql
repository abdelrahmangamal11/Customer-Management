CREATE DATABASE IF NOT EXISTS customer_db;

USE customer_db;

CREATE TABLE IF NOT EXISTS customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT IGNORE INTO customers (name, email, phone, created_at) VALUES
('Ahmed Mohamed',    'ahmed.mohamed@gmail.com',    '01012345678', '2025-01-10 09:00:00'),
('Sara Ali',         'sara.ali@yahoo.com',         '01123456789', '2025-01-15 10:30:00'),
('Mohamed Hassan',   'mohamed.hassan@outlook.com', '01234567890', '2025-02-01 11:00:00'),
('Nour Ibrahim',     'nour.ibrahim@gmail.com',     '01512345678', '2025-02-14 14:00:00'),
('Omar Khaled',      'omar.khaled@hotmail.com',    '01098765432', '2025-03-05 09:30:00'),
('Mona Tarek',       'mona.tarek@gmail.com',       '01187654321', '2025-03-20 12:00:00'),
('Youssef Samir',    'youssef.samir@gmail.com',    '01276543210', '2025-04-01 08:00:00'),
('Layla Mahmoud',    'layla.mahmoud@yahoo.com',    '01565432109', '2025-04-10 16:00:00'),
('Karim Adel',       'karim.adel@outlook.com',     '01054321098', '2025-04-25 11:30:00'),
('Dina Walid',       'dina.walid@gmail.com',       '01143210987', '2025-05-01 13:00:00');