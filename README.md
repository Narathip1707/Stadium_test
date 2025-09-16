# Sports Booking System - โปรเจคระบบจองสนามกีฬา

โปรเจคนี้เป็นระบบจองสนามกีฬาที่พัฒนาด้วย Java Web Application โดยใช้ Jakarta EE 10 และ MySQL Database

## ข้อกำหนดของระบบ (System Requirements)

### Software ที่จำเป็น:
1. **Java Development Kit (JDK) 11 หรือสูงกว่า**
2. **Apache NetBeans IDE 17+** หรือ IDE อื่นที่รองรับ Jakarta EE
3. **GlassFish Server 7.0** (Jakarta EE 10 compatible)
4. **MySQL Server 8.0+** 
5. **MySQL Workbench** (สำหรับจัดการฐานข้อมูล - optional)

## ขั้นตอนการติดตั้งและเรียกใช้งาน

### 1. เตรียมฐานข้อมูล MySQL

#### ติดตั้ง MySQL Server:
- ดาวน์โหลดและติดตั้ง MySQL Server จาก https://dev.mysql.com/downloads/mysql/
- ตั้งค่า root password (หรือใช้ password เปล่าตามโค้ด)
- เริ่มต้นการทำงานของ MySQL Service

#### สร้างฐานข้อมูล:
```sql
-- รันไฟล์ database_setup.sql ที่อยู่ในโฟลเดอร์โปรเจค
-- หรือรันคำสั่งต่อไปนี้ใน MySQL Command Line หรือ MySQL Workbench
source database_setup.sql;
```

#### หรือใช้ MySQL Command Line:
```cmd
mysql -u root -p < database_setup.sql
```

### 2. ตั้งค่า GlassFish Server

#### ดาวน์โหลดและติดตั้ง:
- ดาวน์โหลด GlassFish 7.0 จาก https://javaee.github.io/glassfish/download
- แตกไฟล์และตั้งค่าตัวแปร GLASSFISH_HOME

#### เริ่มต้น GlassFish Server:
```cmd
# ไปที่โฟลเดอร์ GlassFish
cd %GLASSFISH_HOME%\bin
asadmin start-domain
```

### 3. ตั้งค่าโปรเจคใน NetBeans

#### เปิดโปรเจค:
1. เปิด NetBeans IDE
2. File → Open Project
3. เลือกโฟลเดอร์ `SportsBooking`
4. NetBeans จะตรวจจับโปรเจคแบบ Ant อัตโนมัติ

#### ตั้งค่า Server:
1. Right-click บนโปรเจค → Properties
2. ไปที่ Run → Server
3. เลือก GlassFish Server หรือเพิ่ม Server ใหม่
4. กด OK

### 4. Build และ Deploy

#### ใน NetBeans:
1. Right-click บนโปรเจค → Clean and Build
2. หลังจาก Build สำเร็จ → Right-click → Run
3. NetBeans จะ Deploy ไปยัง GlassFish และเปิด Browser อัตโนมัติ

#### Manual Deploy:
```cmd
# Build โปรเจค
ant clean dist

# Deploy ไฟล์ WAR ไปยัง GlassFish
asadmin deploy dist/SportsBooking.war
```

### 5. การเข้าใช้งานระบบ

#### URL ของแอปพลิเคชัน:
```
http://localhost:8080/SportsBooking/
```

#### บัญชีทดสอบ:
- **Admin**: `admin` / `admin123`
- **User**: `user1` / `user123`

## โครงสร้างโปรเจค

```
SportsBooking/
├── build/                 # ไฟล์ที่ Build แล้ว
├── nbproject/            # ตั้งค่า NetBeans
├── src/java/             # Source Code หลัก
│   ├── controller/       # Servlet Controllers
│   ├── dao/             # Data Access Objects
│   ├── db/              # Database Connection
│   └── model/           # Data Models
├── web/                 # Web Resources
│   ├── assets/          # CSS, JS, Images
│   ├── css/            # Style sheets
│   ├── WEB-INF/        # Web configuration
│   └── *.jsp           # JSP Pages
├── build.xml           # Ant Build Script
└── database_setup.sql  # Database Schema
```

## ฟีเจอร์หลักของระบบ

### สำหรับผู้ใช้ทั่วไป:
- ✅ สมัครสมาชิก / เข้าสู่ระบบ
- ✅ ดูข้อมูลสนามกีฬา
- ✅ จองสนาม (วันที่ + เวลา)
- ✅ ชำระเงิน
- ✅ ดูประวัติการจอง
- ✅ ยกเลิกการจอง

### สำหรับผู้ดูแลระบบ:
- ✅ จัดการข้อมูลสนาม (เพิ่ม/แก้ไข/ลบ)
- ✅ จัดการผู้ใช้
- ✅ จัดการการจอง
- ✅ ดูรายงานและสถิติ
- ✅ Dashboard แสดงข้อมูลสรุป

## ฐานข้อมูล

### ตารางหลัก:
- **users**: ข้อมูลผู้ใช้
- **fields**: ข้อมูลสนามกีฬา
- **bookings**: ข้อมูลการจอง
- **payments**: ข้อมูลการชำระเงิน

## การแก้ไขปัญหาที่อาจเกิดขึ้น

### 1. Database Connection Error:
- ตรวจสอบว่า MySQL Server ทำงานอยู่
- ตรวจสอบ username/password ในไฟล์ `DBConnection.java`
- ตรวจสอบว่าฐานข้อมูล `sportsbooking` ถูกสร้างแล้ว

### 2. Server Error 500:
- ตรวจสอบ Log ใน GlassFish: `%GLASSFISH_HOME%\glassfish\domains\domain1\logs\server.log`
- ตรวจสอบว่า JAR dependencies ครบถ้วน

### 3. Encoding Issues (ตัวอักษรไทยผิดเพี้ยน):
- ตั้งค่า MySQL Charset เป็น `utf8mb4`
- ตรวจสอบการตั้งค่า `useUnicode=true&characterEncoding=UTF-8` ใน Database URL

### 4. Build Error:
```cmd
# Clean และ Build ใหม่
ant clean
ant compile
ant dist
```

## การพัฒนาเพิ่มเติม

### เพิ่มฟีเจอร์ใหม่:
1. เพิ่ม Controller ใหม่ใน `src/java/controller/`
2. เพิ่ม DAO สำหรับจัดการฐานข้อมูลใน `src/java/dao/`
3. เพิ่ม JSP page ใน `web/`
4. อัพเดต Database Schema ถ้าจำเป็น

### การ Debug:
- ใช้ NetBeans Debugger
- ดู Log ใน GlassFish Server
- ใช้ Browser Developer Tools สำหรับ Frontend

## ข้อมูลเพิ่มเติม

### ที่มาของโค้ด:
- พัฒนาโดย: Narathip
- Version: 2.67
- Framework: Jakarta EE 10
- Database: MySQL 8.0+
- Server: GlassFish 7.0

### การสนับสนุน:
หากพบปัญหาในการติดตั้งหรือใช้งาน สามารถตรวจสอบ:
1. Log files ใน GlassFish
2. Database connection settings
3. Project dependencies ใน `nbproject/project.properties`