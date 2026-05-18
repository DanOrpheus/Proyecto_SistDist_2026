@echo off
setlocal
cd /d "%~dp0"

echo ========================================
echo  Cargando datos de prueba en MongoDB
echo ========================================
echo.

docker exec ece-mongo mongosh ece_nacional --quiet --eval "db.expedientes.updateOne({curpPaciente:'TEST010101HSONXX01'}, {$set:{curpPaciente:'TEST010101HSONXX01', nombreCompleto:'Paciente de Prueba Docker', tipoSangre:'O+', alergias:['Ninguna'], notasClinicas:'Registro creado desde contenedores Docker'}}, {upsert:true}); print('Expediente de prueba listo: TEST010101HSONXX01');"

echo.
pause
