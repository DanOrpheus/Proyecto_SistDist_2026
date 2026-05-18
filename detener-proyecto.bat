@echo off
setlocal
cd /d "%~dp0"

echo ========================================
echo  Proyecto_SistDist_2026 - Detener
echo ========================================
echo.

docker compose down

echo.
echo Contenedores detenidos.
pause
