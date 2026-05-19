@echo off
setlocal
cd /d "%~dp0"

echo ========================================
echo  Cargando datos de prueba en MongoDB
echo ========================================
echo.

docker cp mongo\seed-ece.js ece-mongo:/tmp/seed-ece.js
if errorlevel 1 (
  echo.
  echo ERROR: No se pudo copiar el archivo seed-ece.js al contenedor ece-mongo.
  echo Verifica que Docker este corriendo y que el proyecto este levantado.
  pause
  exit /b 1
)

docker exec ece-mongo mongosh ece_nacional --quiet /tmp/seed-ece.js
if errorlevel 1 (
  echo.
  echo ERROR: No se pudieron cargar los datos de prueba.
  echo Verifica que el contenedor ece-mongo este corriendo.
  pause
  exit /b 1
)

echo.
pause
