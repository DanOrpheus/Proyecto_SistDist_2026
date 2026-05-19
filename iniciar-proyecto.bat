@echo off
setlocal
cd /d "%~dp0"

echo ========================================
echo  Proyecto_SistDist_2026 - Iniciar
echo ========================================
echo.

echo [1/2] Compilando backend con Maven...
cd ece-backend

set "MVN_CMD="
where mvn >nul 2>nul
if not errorlevel 1 set "MVN_CMD=mvn"

if not defined MVN_CMD if exist "C:\Program Files\NetBeans-25\netbeans\java\maven\bin\mvn.cmd" set "MVN_CMD=C:\Program Files\NetBeans-25\netbeans\java\maven\bin\mvn.cmd"
if not defined MVN_CMD if exist "C:\Program Files\NetBeans-24\netbeans\java\maven\bin\mvn.cmd" set "MVN_CMD=C:\Program Files\NetBeans-24\netbeans\java\maven\bin\mvn.cmd"
if not defined MVN_CMD if exist "C:\Program Files\NetBeans-23\netbeans\java\maven\bin\mvn.cmd" set "MVN_CMD=C:\Program Files\NetBeans-23\netbeans\java\maven\bin\mvn.cmd"
if not defined MVN_CMD if exist "C:\Program Files\NetBeans-22\netbeans\java\maven\bin\mvn.cmd" set "MVN_CMD=C:\Program Files\NetBeans-22\netbeans\java\maven\bin\mvn.cmd"

if not defined MVN_CMD (
  echo.
  echo ERROR: No se encontro Maven.
  echo Instala Maven o NetBeans 22, 23, 24 o 25, o agrega mvn al PATH.
  echo Tambien puedes compilar manualmente desde ece-backend con:
  echo mvn --no-transfer-progress package -DskipTests
  pause
  exit /b 1
)

echo Usando Maven: %MVN_CMD%
call %MVN_CMD% --no-transfer-progress package -DskipTests
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
