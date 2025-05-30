# Backend - Notes App Web

Este directorio contiene el backend de la aplicación *Notes App Web*.  

La arquitectura sigue una separación en capas (Controllers, Services, Repositories) y usa JPA/Hibernate para la persistencia.

---

## Funcionalidades

- CRUD (Crear, leer, actualizar y eliminar) de notas.
- Operaciones para archivar/desarchivar, marcar como completadas y cambiar la prioridad.
- Gestión y filtrado de notas por categoría, fecha límite y estado (activo/archivado).

---

## ⚙️ Tecnologías utilizadas

- Java 21
- Spring Boot 3.5.0
- Maven 3.9.9 (`./mvnw`)
- MySQL 8.x
- Hibernate / JPA
- Lombok

---

## ▶️ Requisitos previos

| Requisito | Versión recomendada |
|-----------|---------------------|
| Java      | 21                  |
| MySQL     | 8.x                 |

> ⚠️ Maven se ejecuta mediante wrapper (`./mvnw` o `./mvnw.cmd`), no es necesario instalarlo manualmente.

---

## 🗃️ Base de datos

- Nombre: `ensolvers_notes`
- Usuario: `root`
- Contraseña: `admin`

> Estos datos pueden modificarse en `application.properties`.

---

## 🚀 Cómo ejecutar

Desde la raíz del proyecto:

```bash
./run-backend.sh
```

🖥️ Compatibilidad del script run-backend.sh
El archivo run-backend.sh está diseñado para funcionar en Windows, Linux y macOS, con las siguientes consideraciones:

🪟 Windows (Git Bash)
Asegurate de que el archivo tenga permisos de ejecución: 
chmod +x run-backend.sh

Si tu instalación de MySQL está en una ruta personalizada (como XAMPP), editá esta línea en run-backend.sh:
MYSQL_PATH="/c/Program Files/MySQL/MySQL Server 8.0/bin/mysql"

Reemplazala por tu ruta real, por ejemplo:
MYSQL_PATH="/c/xampp/mysql/bin/mysql"

El script usa cmd.exe /C "mvnw.cmd spring-boot:run" para ejecutar correctamente Maven incluso si hay espacios en tu nombre de usuario.

🐧 Linux / 🍎 macOS
El script usa directamente ./mvnw para compilar y ejecutar.
Asegurate de tener instalado el cliente de MySQL:

sudo apt install mysql-client      # Linux
brew install mysql                 # macOS

✅ El script detecta automáticamente el sistema operativo y usa el wrapper Maven adecuado.

---

## 🌐 Endpoint principal

Una vez iniciado el backend, el servidor se ejecuta en:

http://localhost:8080


Los endpoints REST pueden probarse mediante Postman o directamente desde el frontend.

---

> ⚠️ Este backend no incluye autenticación ni interfaz HTML. Es consumido 100% por el frontend vía API REST.

---

## 👨‍💻 Desarrollado por Felipe Martín Lucena

---