# 🗄️ Database Structure Documentation

## 📋 Overview
Sports Booking System ใช้ MySQL 8.0+ เป็นฐานข้อมูลหลัก ประกอบด้วย 4 ตารางหลัก

## 🏗️ Database Schema

### 📊 ER Diagram
```
┌─────────────┐    1    ∞  ┌─────────────┐    1    0..1 ┌─────────────┐
│    USERS    │────────────│   BOOKINGS  │──────────────│  PAYMENTS   │
│             │            │             │              │             │
│ • id (PK)   │            │ • bookingId │              │ • paymentId │
│ • username  │            │ • userId    │              │ • bookingId │
│ • email     │            │ • fieldType │              │ • amount    │
│ • isAdmin   │            │ • totalPrice│              │ • status    │
│ • ...       │            │ • ...       │              │ • ...       │
└─────────────┘            └─────────────┘              └─────────────┘
                                  │
                                  │ ∞
                                  │
                                  │ 1
                           ┌─────────────┐
                           │   FIELDS    │
                           │             │
                           │ • id (PK)   │
                           │ • fieldType │
                           │ • name      │
                           │ • price     │
                           │ • ...       │
                           └─────────────┘
```

## 📁 Table Structures

### 👤 users
ตารางเก็บข้อมูลผู้ใช้ระบบ

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | INT | PRIMARY KEY, AUTO_INCREMENT | รหัสผู้ใช้ |
| `username` | VARCHAR(50) | UNIQUE, NOT NULL | ชื่อผู้ใช้ |
| `email` | VARCHAR(100) | UNIQUE, NOT NULL | อีเมล |
| `phone` | VARCHAR(20) | NULL | หมายเลขโทรศัพท์ |
| `firstName` | VARCHAR(50) | NOT NULL | ชื่อจริง |
| `lastName` | VARCHAR(50) | NOT NULL | นามสกุล |
| `password` | VARCHAR(255) | NOT NULL | รหัสผ่าน |
| `isAdmin` | BOOLEAN | DEFAULT FALSE | สิทธิ์ผู้ดูแลระบบ |
| `createdAt` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | วันที่สร้าง |

**Sample Data:**
```sql
INSERT INTO users VALUES 
(1, 'admin', 'admin@sportsbooking.com', '0812345678', 'Admin', 'User', 'admin123', TRUE, NOW()),
(2, 'user1', 'user1@example.com', '0987654321', 'John', 'Doe', 'user123', FALSE, NOW());
```

### 🏟️ fields
ตารางเก็บข้อมูลสนามกีฬา

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `id` | INT | PRIMARY KEY, AUTO_INCREMENT | รหัสสนาม |
| `fieldType` | VARCHAR(50) | NOT NULL | ประเภทสนาม |
| `name` | VARCHAR(100) | NOT NULL | ชื่อสนาม |
| `description` | TEXT | NULL | รายละเอียด |
| `price` | DECIMAL(10,2) | NOT NULL | ราคาต่อชั่วโมง |
| `location` | VARCHAR(255) | NULL | ที่ตั้ง |
| `imageUrl` | VARCHAR(255) | NULL | รูปภาพ |
| `status` | VARCHAR(20) | DEFAULT 'active' | สถานะ |
| `capacity` | INT | NULL | ความจุ |
| `operatingHours` | VARCHAR(100) | NULL | เวลาทำการ |
| `createdAt` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | วันที่สร้าง |

**Field Types:**
- `FOOTBALL` - สนามฟุตบอล
- `BASKETBALL` - สนามบาสเก็ตบอล
- `TENNIS` - สนามเทนนิส
- `BADMINTON` - สนามแบดมินตัน

### 📅 bookings
ตารางเก็บข้อมูลการจอง

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `bookingId` | INT | PRIMARY KEY, AUTO_INCREMENT | รหัสการจอง |
| `userId` | INT | FOREIGN KEY (users.id) | รหัสผู้จอง |
| `fieldType` | VARCHAR(50) | NOT NULL | ประเภทสนาม |
| `bookingDate` | DATE | NOT NULL | วันที่จอง |
| `startTime` | TIME | NOT NULL | เวลาเริ่ม |
| `endTime` | TIME | NOT NULL | เวลาสิ้นสุด |
| `status` | VARCHAR(20) | DEFAULT 'pending' | สถานะการจอง |
| `totalPrice` | INT | NOT NULL | ราคารวม |
| `paymentStatus` | VARCHAR(20) | DEFAULT 'unpaid' | สถานะการชำระเงิน |
| `createdAt` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | วันที่จอง |

**Booking Status:**
- `pending` - รอยืนยัน
- `confirmed` - ยืนยันแล้ว
- `cancelled` - ยกเลิก
- `completed` - เสร็จสิ้น

**Payment Status:**
- `unpaid` - ยังไม่ชำระ
- `paid` - ชำระแล้ว
- `cancelled` - ยกเลิก

### 💳 payments
ตารางเก็บข้อมูลการชำระเงิน

| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| `paymentId` | INT | PRIMARY KEY, AUTO_INCREMENT | รหัสการชำระเงิน |
| `bookingId` | INT | FOREIGN KEY (bookings.bookingId) | รหัสการจอง |
| `amount` | DECIMAL(10,2) | NOT NULL | จำนวนเงิน |
| `paymentMethod` | VARCHAR(50) | NOT NULL | วิธีชำระเงิน |
| `transactionId` | VARCHAR(100) | UNIQUE | รหัสธุรกรรม |
| `paymentStatus` | VARCHAR(20) | DEFAULT 'pending' | สถานะการชำระเงิน |
| `paymentDate` | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | วันที่ชำระเงิน |

**Payment Methods:**
- `QR_CODE` - QR Code
- `BANK_TRANSFER` - โอนเงิน
- `CREDIT_CARD` - บัตรเครดิต

**Payment Status:**
- `pending` - รออนุมัติ
- `completed` - สำเร็จ
- `failed` - ล้มเหลว
- `refunded` - คืนเงิน

## 🔗 Relationships

### Foreign Key Constraints
```sql
-- bookings.userId → users.id
ALTER TABLE bookings 
ADD CONSTRAINT fk_booking_user 
FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE;

-- payments.bookingId → bookings.bookingId
ALTER TABLE payments 
ADD CONSTRAINT fk_payment_booking 
FOREIGN KEY (bookingId) REFERENCES bookings(bookingId) ON DELETE CASCADE;
```

### Business Rules
1. **User-Booking**: ผู้ใช้หนึ่งคนสามารถมีการจองได้หลายครั้ง (1:N)
2. **Booking-Payment**: การจองหนึ่งครั้งมีการชำระเงินได้หนึ่งครั้ง (1:1)
3. **Field-Booking**: สนามหนึ่งสามารถถูกจองได้หลายครั้ง (1:N)

## 📊 Indexes

### Primary Indexes
- `users.id` (PRIMARY KEY)
- `fields.id` (PRIMARY KEY)
- `bookings.bookingId` (PRIMARY KEY)
- `payments.paymentId` (PRIMARY KEY)

### Secondary Indexes
```sql
-- Performance optimization
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_bookings_user ON bookings(userId);
CREATE INDEX idx_bookings_date ON bookings(bookingDate);
CREATE INDEX idx_bookings_status ON bookings(status, paymentStatus);
CREATE INDEX idx_payments_booking ON payments(bookingId);
CREATE INDEX idx_payments_status ON payments(paymentStatus);
```

## 🔍 Common Queries

### User Authentication
```sql
SELECT id, username, firstName, lastName, isAdmin 
FROM users 
WHERE username = ? AND password = ?;
```

### Available Fields
```sql
SELECT * FROM fields 
WHERE status = 'active' 
ORDER BY fieldType, name;
```

### Check Booking Availability
```sql
SELECT COUNT(*) FROM bookings 
WHERE fieldType = ? 
  AND bookingDate = ? 
  AND status != 'cancelled'
  AND (
    (startTime <= ? AND endTime > ?) OR
    (startTime < ? AND endTime >= ?) OR
    (startTime >= ? AND endTime <= ?)
  );
```

### User Booking History
```sql
SELECT b.*, f.name as fieldName, p.paymentStatus as paymentStatus
FROM bookings b
LEFT JOIN fields f ON b.fieldType = f.fieldType
LEFT JOIN payments p ON b.bookingId = p.bookingId
WHERE b.userId = ?
ORDER BY b.createdAt DESC;
```

### Revenue Report
```sql
SELECT 
  DATE_FORMAT(paymentDate, '%Y-%m') as month,
  COUNT(*) as totalPayments,
  SUM(amount) as totalRevenue
FROM payments 
WHERE paymentStatus = 'completed'
  AND paymentDate >= DATE_SUB(NOW(), INTERVAL 12 MONTH)
GROUP BY DATE_FORMAT(paymentDate, '%Y-%m')
ORDER BY month DESC;
```

## 🛡️ Security Considerations

### Data Protection
- Passwords ควรใช้ hashing (BCrypt/SHA-256)
- Sensitive data ควร encrypt
- Regular backup ทุก 24 ชั่วโมง

### Access Control
- Database user permissions ตาม role
- Connection pooling สำหรับ performance
- SQL injection prevention

### Audit Trail
- Log การเปลี่ยนแปลงข้อมูลสำคัญ
- Track user activities
- Monitor failed login attempts

## 📈 Performance Optimization

### Query Optimization
- ใช้ prepared statements
- Optimize JOIN queries
- Implement proper indexing

### Connection Management
- Connection pooling
- Connection timeout settings
- Maximum connections limit

### Maintenance
- Regular table optimization
- Index rebuilding
- Statistics updates

---

*Last updated: 2024*