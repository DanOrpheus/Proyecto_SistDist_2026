@echo off
setlocal
cd /d "%~dp0"

echo ========================================
echo  Cargando datos de prueba en MongoDB
echo ========================================
echo.

docker exec -i ece-mongo mongosh ece_nacional --quiet < mongo\seed-ece.js

echo.
pause
