@echo off
setlocal
cd /d "%~dp0"

echo ========================================
echo  Proyecto_SistDist_2026 - Iniciar
echo ========================================
echo.

echo [1/2] Compilando backend con Maven...
cd ece-backend
"C:\Program Files\NetBeans-25\netbeans\java\maven\bin\mvn.cmd" --no-transfer-progress package -DskipTests
if errorlevel 1 (
  echo.
  echo ERROR: Fallo la compilacion del backend.
  echo Revisa el mensaje de arriba.
  pause
  exit /b 1
)

cd ..
echo.
echo [2/2] Levantando MongoDB y Quarkus con Docker Compose...
echo Cuando veas que ece-backend esta escuchando en http://0.0.0.0:8080, abre http://localhost:8080
echo.
docker compose up --build

echo.
echo El proceso termino.
pause
