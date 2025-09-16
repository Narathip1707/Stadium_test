@echo off
echo Creating project structure...

if not exist "docs" mkdir docs
if not exist "docs\diagrams" mkdir docs\diagrams
if not exist "docs\api" mkdir docs\api

echo Directory structure created!
echo.
echo 📁 Created directories:
echo  • docs\
echo  • docs\diagrams\
echo  • docs\api\
echo.
echo You can now add your documentation files:
echo  • Use Case diagrams in docs\diagrams\
echo  • Sequence diagrams in docs\diagrams\  
echo  • API documentation in docs\api\
echo.
pause