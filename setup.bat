@echo off
echo ===================================
echo Sports Booking System Setup Guide
echo ===================================

echo.
echo นี่คือคู่มือการติดตั้งและเรียกใช้งานระบบจองสนามกีฬา
echo.

echo 1. ติดตั้ง MySQL Server และสร้างฐานข้อมูล:
echo    - ดาวน์โหลดและติดตั้ง MySQL Server
echo    - รันคำสั่ง: mysql -u root -p ^< database_setup.sql
echo.

echo 2. ติดตั้ง GlassFish Server 7.0:
echo    - ดาวน์โหลดจาก https://javaee.github.io/glassfish/download
echo    - แตกไฟล์และเริ่มต้น Server: asadmin start-domain
echo.

echo 3. Build และ Deploy โปรเจค:
echo    - รันคำสั่ง: .\build.bat (สร้าง WAR file)
echo    - Deploy: asadmin deploy dist\SportsBooking.war
echo    - หรือใช้ NetBeans: เปิดโปรเจค → Run
echo.

echo 4. เข้าใช้งานระบบ:
echo    - URL: http://localhost:8080/SportsBooking/
echo    - Admin: admin / admin123
echo    - User: user1 / user123
echo.

echo หมายเหตุ: โค้ดนี้ถูกสร้างสำหรับ NetBeans และใช้งานได้บน GlassFish
echo การ Build ด้วย build.bat อาจมี compilation warnings แต่ยังสามารถสร้าง WAR file ได้
echo สำหรับการใช้งานจริง แนะนำให้ใช้ NetBeans IDE

echo.
pause