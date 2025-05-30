# Notes App Web

La aplicación está dividida en dos partes:

- **Frontend (SPA):** Implementado en HTML, CSS y JavaScript (Vanilla).  
  Permite crear, editar, eliminar, archivar/desarchivar y filtrar notas (incluyendo la asignación de categorías).

- **Backend:** Implementado con Java, Spring Boot y MySQL.  
  Expone un API REST que realiza operaciones CRUD sobre las notas y gestiona el filtrado según parámetros (categoría, fecha, etc).

Además, se incluyeron scripts para facilitar la ejecución:
- `run-frontend.sh`: Script para abrir el frontend en Google Chrome.
- `run-backend.sh`: Script para levantar el backend y crear la base de datos si no existe.
- `run-all.sh`: Levanta el backend y, tras esperar unos segundos, abre el frontend.

---

## Consideraciones y Requisitos

### Tecnologías utilizadas:
- **Frontend:**
    - HTML5, CSS3, JavaScript (Vanilla)
    - Bootstrap 5.3, Font Awesome 6.5
- **Backend:**
    - Java 21, Spring Boot 3.5.0, Maven
    - MySQL 8.x, Hibernate/JPA
    - Lombok

### Requisitos del sistema:
- **Java:** versión 21
- **MySQL:** versión 8.x (El archivo `application.properties` tiene la configuración, y podés modificar el usuario y la contraseña según tu entorno)
- **Maven:** a través del wrapper (`./mvnw` o `mvnw.cmd`)
- **Sistema operativo:** Linux/macOS/Windows (los scripts se adaptan para Windows usando Git Bash)

---

## Ejecutar la aplicación

### Backend:
En el directorio `backend` se encuentra el script `run-backend.sh` que:
- Crea la base de datos (si no existe)
- Levanta el servidor Spring Boot en el puerto 8080

### Frontend:
En el directorio `frontend` se encuentra el script `run-frontend.sh` que:
- Abre directamente el archivo `index.html` en Google Chrome (se configura la ruta de Chrome en el script)

### Ejecución conjunta (Opcional):
El script `run-all.sh` en la raíz permite ejecutar:
1. El backend (levantándolo en segundo plano)
2. Una pausa para que se inicialice Spring Boot
3. La apertura del frontend en Google Chrome

---

## Estructura del Proyecto

/ (raíz)
├── backend
│ ├── src/
│ ├── pom.xml
│ ├── run-backend.sh
│ └── README.md <-- (Ver instrucciones específicas del backend)
├── frontend
│ ├── index.html
│ ├── script.js
│ ├── styles.css
│ ├── assets/
│ ├── run-frontend.sh
│ └── README.md <-- (Ver instrucciones específicas del frontend)
└── run-all.sh (Para levantar backend y frontend juntos. Espera de 10 segundos)

---

## Notas adicionales
- **Scripts:** Asegurate de dar permisos de ejecución usando `chmod +x` en Linux/macOS o Git Bash en Windows.
- **Personalización:** Las rutas a Google Chrome y MySQL pueden necesitar ajuste según la instalación en tu sistema.
- **API:** El backend no posee una interfaz visual propia, ya que expone un API REST consumido por el frontend.

---

## 👨‍💻 Desarrollado por Felipe Martín Lucena

---

# Las pruebas fueron testeadas en WINDOWS. Los scripts fueron diseñados tambien para el soporte de macOS/Linux