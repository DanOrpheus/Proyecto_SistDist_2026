# Proyecto_SistDist_2026
Proyecto de Sistemas Distribuidos | Enero-Mayo 2026

## Ejecucion con Docker

Este proyecto puede levantarse con contenedores usando Docker Compose. La
configuracion crea dos servicios:

- `mongo`: base de datos MongoDB para almacenar los expedientes clinicos.
- `ece-backend`: backend Quarkus que expone la API REST.

Para construir y ejecutar los contenedores:

```bash
docker compose up --build
```

En Windows tambien se puede ejecutar:

```text
iniciar-proyecto.bat
```

Ese script busca Maven en el PATH o dentro de NetBeans 22, 23, 24 o 25.

El backend queda disponible en:

```text
http://localhost:8080
```

MongoDB queda disponible localmente en:

```text
mongodb://localhost:27017
```

Dentro de la red de Docker, el backend se conecta a MongoDB usando el nombre del
servicio:

```text
mongodb://mongo:27017
```

Para detener los contenedores:

```bash
docker compose down
```

Para detenerlos y eliminar tambien el volumen de datos de MongoDB:

```bash
docker compose down -v
```

## Datos de prueba

Con el proyecto levantado, carga los expedientes de prueba con:

```text
cargar-datos-prueba.bat
```

Credenciales principales:

```text
sofia.martinez@salud.gob.mx / Sofia#928374
ivan.ochoa@ece.mx / Ivan#1997
mariana.ruiz@ece.mx / Tutor#2012
valeria.ramirez@ece.mx / Valeria#2012
```
