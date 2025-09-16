# 🏟️ Sports Booking System

<div align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java" alt="Java">
  <img src="https://img.shields.io/badge/Jakarta%20EE-10-blue?style=for-the-badge&logo=eclipse" alt="Jakarta EE">
  <img src="https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql" alt="MySQL">
  <img src="https://img.shields.io/badge/GlassFish-7.0-green?style=for-the-badge&logo=eclipse" alt="GlassFish">
</div>

## 📖 Project Description

ระบบจองสนามกีฬาออนไลน์ที่พัฒนาด้วย Jakarta EE และ MySQL รองรับการจองสนามหลากหลายประเภท พร้อมระบบชำระเงินและการติดตามสถานะแบบเรียลไทม์

## ✨ Features

### 👤 User Management
- สมัครสมาชิกและเข้าสู่ระบบ
- จัดการโปรไฟล์ส่วนตัว
- ระบบสิทธิ์ผู้ใช้ (User/Admin)

### 🏟️ Field Management
- ดูข้อมูลสนามกีฬาทุกประเภท
- ค้นหาสนามตามเงื่อนไข
- จัดการข้อมูลสนาม (Admin)

### 📅 Booking System
- จองสนามแบบเรียลไทม์
- ตรวจสอบความพร้อมของสนาม
- ประวัติการจองและการยกเลิก

### 💳 Payment Integration
- ชำระเงินผ่าน QR Code
- ชำระเงินผ่านโอนเงิน
- ระบบติดตามสถานะการชำระเงิน

### 👨‍💼 Admin Dashboard
- จัดการผู้ใช้และการจอง
- รายงานสถิติและรายได้
- จัดการข้อมูลสนามกีฬา

### 📦 Tracking System
- ติดตามสถานะการจองแบบเรียลไทม์
- เชื่อมต่อกับระบบ Logistic ภายนอก
- หมายเลขติดตามสำหรับทุกการจอง

## 🛠️ Technology Stack

| Component | Technology | Version |
|-----------|------------|---------|
| **Backend** | Jakarta EE | 10 |
| **Web Framework** | Servlets & JSP | 5.0 |
| **Database** | MySQL | 8.0+ |
| **Application Server** | GlassFish | 7.0+ |
| **Frontend** | HTML5, CSS3, JavaScript | Latest |
| **Build Tool** | Apache Ant | 1.10+ |
| **JSON Processing** | JSON-P | 2.0 |
| **Logging** | SLF4J + Logback | 1.2+ |

## 🚀 Quick Start

### Prerequisites
- Java 17+
- MySQL 8.0+
- GlassFish Server 7.0+
- Git

### Installation

1. **Clone Repository**
```bash
git clone https://github.com/YOUR_USERNAME/SportsBooking.git
cd SportsBooking
```

2. **Database Setup**
```bash
# เข้าสู่ MySQL
mysql -u root -p

# สร้างฐานข้อมูลและตาราง
source database/database_setup.sql
```

3. **Build Project**
```bash
# Windows
build.bat

# หรือใช้ Ant
ant clean compile war
```

4. **Deploy to GlassFish**
```bash
# เริ่ม GlassFish Server
asadmin start-domain

# Deploy WAR file
asadmin deploy dist/SportsBooking.war

# หรือใช้ Admin Console
# http://localhost:4848
```

5. **Access Application**
```
http://localhost:8080/SportsBooking/
```

## 👥 Default Accounts

| Role | Username | Password | Description |
|------|----------|----------|-------------|
| **Admin** | `admin` | `admin123` | ผู้ดูแลระบบ |
| **User** | `user1` | `user123` | ผู้ใช้ทั่วไป |
| **User** | `john.doe` | `password123` | ผู้ใช้ตัวอย่าง |

## 📁 Project Structure

```
SportsBooking/
├── 📂 src/java/                    # Java Source Code
│   ├── 📂 controller/              # Servlet Controllers
│   │   ├── AdminServlet.java
│   │   ├── BookingServlet.java
│   │   ├── LoginServlet.java
│   │   └── PaymentServlet.java
│   ├── 📂 dao/                     # Data Access Objects
│   │   ├── UserDAO.java
│   │   ├── FieldDAO.java
│   │   └── BookingDAO.java
│   ├── 📂 model/                   # Entity Models
│   │   ├── User.java
│   │   ├── Field.java
│   │   └── Booking.java
│   └── 📂 util/                    # Utility Classes
│       └── DBConnection.java
├── 📂 web/                         # Web Resources
│   ├── 📂 WEB-INF/
│   │   ├── 📂 lib/                 # JAR Dependencies
│   │   └── web.xml
│   ├── 📂 css/                     # Stylesheets
│   ├── 📂 js/                      # JavaScript Files
│   ├── 📂 images/                  # Image Assets
│   └── 📄 *.jsp                    # JSP Pages
├── 📂 database/                    # Database Scripts
│   └── database_setup.sql
├── 📂 docs/                        # Documentation
│   ├── 📂 diagrams/               # UML Diagrams
│   └── 📄 API_Documentation.md
├── 📄 build.xml                    # Ant Build Script
├── 📄 build.bat                    # Windows Build Script
└── 📄 README.md                    # This File
```

## 🔧 Configuration

### Database Configuration
แก้ไขไฟล์ `src/java/util/DBConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/sports_booking";
private static final String USERNAME = "your_username";
private static final String PASSWORD = "your_password";
```

### Server Configuration
- **Port**: 8080 (default)
- **Context Path**: `/SportsBooking`
- **Session Timeout**: 30 minutes

## 📊 Database Schema

### Core Tables
- **users** - ข้อมูลผู้ใช้
- **fields** - ข้อมูลสนามกีฬา
- **bookings** - ข้อมูลการจอง
- **payments** - ข้อมูลการชำระเงิน

### Sample Data
- 2 ผู้ใช้ (admin + user)
- 4 สนามกีฬา (ฟุตบอล, บาสเก็ตบอล, เทนนิส, แบดมินตัน)
- ข้อมูลตัวอย่างสำหรับทดสอบ

## 🔐 Security Features

- Session-based Authentication
- Password Hashing
- SQL Injection Prevention
- XSS Protection
- CSRF Token Validation
- Role-based Access Control

## 🧪 Testing

### Manual Testing
1. สมัครสมาชิกและเข้าสู่ระบบ
2. ทดสอบการจองสนาม
3. ทดสอบการชำระเงิน
4. ทดสอบฟังก์ชัน Admin

### Test Accounts
ใช้ default accounts ที่ระบุไว้ข้างต้น

## 🚀 Deployment

### Production Deployment
1. อัพเดต database configuration
2. เปลี่ยน logging level
3. ตั้งค่า security headers
4. กำหนดค่า SSL/TLS

### Docker Deployment (Optional)
```bash
# สร้าง Docker image
docker build -t sports-booking .

# รัน container
docker run -p 8080:8080 sports-booking
```

## 🤝 Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Authors

- **Your Name** - *Initial work* - [YourGitHub](https://github.com/yourusername)

## 🙏 Acknowledgments

- Jakarta EE Community
- MySQL Development Team
- GlassFish Project
- NetBeans IDE

## 📞 Support

If you have any questions or need help, please:

1. Check the [Issues](https://github.com/yourusername/SportsBooking/issues) page
2. Create a new issue if needed
3. Contact: narathip170747@gmail.com

---

<div align="center">
  <p>Made with ❤️ for sports enthusiasts</p>
  <p>⭐ Star this repository if it helped you!</p>
</div>