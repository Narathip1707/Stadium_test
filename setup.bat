@echo off
echo ========================================
echo 🏟️ Sports Booking System Setup Guide
echo ========================================
echo.

echo 📋 System Requirements:
echo  • Java 17 or higher
echo  • MySQL 8.0 or higher  
echo  • GlassFish Server 7.0 or higher
echo  • Git (for version control)
echo.

echo 🔧 Installation Steps:
echo.
echo 1. Database Setup:
echo    mysql -u root -p ^< database\database_setup.sql
echo.
echo 2. Build Project:
echo    build.bat
echo.
echo 3. Deploy to GlassFish:
echo    asadmin start-domain
echo    asadmin deploy dist\SportsBooking.war
echo.
echo 4. Access Application:
echo    http://localhost:8080/SportsBooking/
echo.

echo 👥 Default Login Accounts:
echo  • Admin: admin / admin123
echo  • User:  user1 / user123
echo.

echo 📚 Documentation:
echo  • README.md - Project overview
echo  • docs\database_structure.md - Database documentation
echo  • docs\diagrams\ - UML diagrams
echo.

echo 🆘 Troubleshooting:
echo  • Check if MySQL service is running
echo  • Verify GlassFish server is started
echo  • Check database connection settings
echo  • Review server logs for errors
echo.

echo 📞 Support:
echo  • GitHub Issues: Create issue for bugs/questions
echo  • Documentation: Check docs\ folder
echo.

echo ========================================
echo Setup guide completed! 🎉
echo ========================================
pause