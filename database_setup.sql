-- สร้างฐานข้อมูล Sports Booking System
CREATE DATABASE IF NOT EXISTS sportsbooking 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE sportsbooking;

-- ตาราง users (ข้อมูลผู้ใช้)
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_admin TINYINT(1) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ตาราง fields (ข้อมูลสนาม)
CREATE TABLE IF NOT EXISTS fields (
    id INT AUTO_INCREMENT PRIMARY KEY,
    field_type VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price_per_hour INT NOT NULL,
    location VARCHAR(255),
    image_path VARCHAR(255),
    status ENUM('active', 'inactive') DEFAULT 'active',
    max_players INT DEFAULT 10,
    opening_hours VARCHAR(50) DEFAULT '08:00-22:00',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ตาราง bookings (ข้อมูลการจอง)
CREATE TABLE IF NOT EXISTS bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    field_type VARCHAR(50) NOT NULL,
    booking_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    total_price INT NOT NULL,
    status ENUM('pending', 'confirmed', 'cancelled', 'completed') DEFAULT 'pending',
    payment_status ENUM('pending', 'paid', 'cancelled') DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ตาราง payments (ข้อมูลการชำระเงิน)
CREATE TABLE IF NOT EXISTS payments (
    id INT AUTO_INCREMENT PRIMARY KEY,  
    booking_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    transaction_id VARCHAR(100),
    payment_status ENUM('pending', 'completed', 'failed', 'cancelled') DEFAULT 'pending',
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE CASCADE
);

-- เพิ่มข้อมูลตัวอย่าง
-- Admin user (username: admin, password: admin123)
INSERT INTO users (username, email, phone, first_name, last_name, password, is_admin) VALUES
('admin', 'admin@sportsbooking.com', '0800000000', 'Admin', 'System', 'admin123', 1);

-- Sample regular user (username: user1, password: user123)
INSERT INTO users (username, email, phone, first_name, last_name, password, is_admin) VALUES
('user1', 'user1@example.com', '0812345678', 'สมชาย', 'ใจดี', 'user123', 0);

-- ข้อมูลสนามตัวอย่าง
INSERT INTO fields (field_type, name, description, price_per_hour, location, image_path, status, max_players, opening_hours) VALUES
('FOOTBALL', 'สนามฟุตบอลหญ้าเทียม 1', 'สนามฟุตบอลขนาดมาตรฐาน หтравاเทียมคุณภาพสูง', 500, 'สนามกีฬาแห่งชาติ', 'assets/images/football_field_1.jpg', 'active', 22, '06:00-23:00'),
('FOOTBALL', 'สนามฟุตบอลหญ้าเทียม 2', 'สนามฟุตบอลขนาดมาตรฐาน หญ้าเทียมคุณภาพสูง พร้อมไฟส่องสว่าง', 550, 'สนามกีฬาแห่งชาติ', 'assets/images/football_field_2.jpg', 'active', 22, '06:00-23:00'),
('BASKETBALL', 'สนามบาสเก็ตบอล Indoor', 'สนามบาสเก็ตบอลในร่ม พื้นไม้ คุณภาพสูง', 300, 'ศูนย์กีฬาใหม่', 'assets/images/basketball_indoor.jpg', 'active', 10, '08:00-22:00'),
('VOLLEYBALL', 'สนามวอลเลย์บอล', 'สนามวอลเลย์บอลมาตรฐาน พร้อมตาข่าย', 250, 'สนามกีฬาชุมชน', 'assets/images/volleyball_court.jpg', 'active', 12, '07:00-21:00'),
('BADMINTON', 'คอร์ตแบดมินตัน 1', 'คอร์ตแบดมินตันในร่ม พื้นไม้ยางพารา', 200, 'ศูนย์แบดมินตัน', 'assets/images/badminton_court_1.jpg', 'active', 4, '08:00-22:00'),
('BADMINTON', 'คอร์ตแบดมินตัน 2', 'คอร์ตแบดมินตันในร่ม พื้นไม้ยางพารา พร้อมแอร์', 220, 'ศูนย์แบดมินตัน', 'assets/images/badminton_court_2.jpg', 'active', 4, '08:00-22:00'),
('TENNIS', 'สนามเทนนิส', 'สนามเทนนิสพื้นคอนกรีต มาตรฐาน ITF', 400, 'สโมสรเทนนิส', 'assets/images/tennis_court.jpg', 'active', 4, '06:00-20:00');

-- ข้อมูลการจองตัวอย่าง
INSERT INTO bookings (user_id, field_type, booking_date, start_time, end_time, total_price, status, payment_status) VALUES
(2, 'FOOTBALL', '2025-09-20', '08:00:00', '10:00:00', 1000, 'confirmed', 'paid'),
(2, 'BASKETBALL', '2025-09-22', '14:00:00', '16:00:00', 600, 'pending', 'pending');

-- ข้อมูลการชำระเงินตัวอย่าง
INSERT INTO payments (booking_id, amount, payment_method, transaction_id, payment_status) VALUES
(1, 1000.00, 'credit_card', 'TXN_001_20250920', 'completed');

-- สร้าง index เพื่อประสิทธิภาพ
CREATE INDEX idx_bookings_date ON bookings(booking_date);
CREATE INDEX idx_bookings_user ON bookings(user_id);
CREATE INDEX idx_bookings_field_type ON bookings(field_type);
CREATE INDEX idx_payments_booking ON payments(booking_id);
CREATE INDEX idx_fields_type ON fields(field_type);