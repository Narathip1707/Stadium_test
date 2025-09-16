@echo off
echo ===================================
echo Sports Booking System Build Script
echo ===================================

echo.
echo Checking Java installation...
java -version
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH
    pause
    exit /b 1
)

echo.
echo Creating build directories...
if not exist "build\web\WEB-INF\classes" mkdir "build\web\WEB-INF\classes"
if not exist "build\web\WEB-INF\lib" mkdir "build\web\WEB-INF\lib"
if not exist "dist" mkdir "dist"

echo.
echo Copying web resources...
xcopy /E /I /Y "web\*" "build\web\" > nul
if errorlevel 1 (
    echo ERROR: Failed to copy web resources
    pause
    exit /b 1
)

echo.
echo Compiling Java sources...
set CLASSPATH=web\WEB-INF\lib\*;
for /r "src\java" %%i in (*.java) do (
    javac -cp "%CLASSPATH%" -d "build\web\WEB-INF\classes" "%%i"
)
if errorlevel 1 (
    echo ERROR: Compilation failed
    pause
    exit /b 1
)

echo.
echo Creating WAR file...
cd build\web
jar -cf "..\..\dist\SportsBooking.war" *
cd ..\..

if exist "dist\SportsBooking.war" (
    echo.
    echo ===================================
    echo BUILD SUCCESS!
    echo ===================================
    echo WAR file created: dist\SportsBooking.war
    echo.
    echo Next steps:
    echo 1. Install and start GlassFish Server
    echo 2. Deploy: asadmin deploy dist\SportsBooking.war
    echo 3. Access: http://localhost:8080/SportsBooking/
    echo.
) else (
    echo ERROR: WAR file creation failed
    pause
    exit /b 1
)

pause