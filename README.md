# ROAR NAILS — Schedule Manager

ROAR NAILS Schedule Manager es un sistema web de gestión de citas desarrollado
para un salón de uñas. Permite registrar clientes, definir servicios y agendar
citas con control de estados (pendiente, confirmada, cancelada, completada).
Construido con Java 21 y Spring Boot 3 en el backend, PostgreSQL como base de
datos relacional, y HTML con Bootstrap 5 en el frontend. Implementa los
principios de Programación Orientada a Objetos — encapsulación, composición y
separación de responsabilidades — a través de una arquitectura en capas: modelo,
repositorio, servicio y controlador. Incluye pruebas unitarias con JUnit 5 y
Mockito, e integración continua con GitHub Actions.

## Problema identificado

Los salones de uñas pequeños gestionan sus citas manualmente por WhatsApp o
llamadas telefónicas, lo que genera errores de agenda, citas duplicadas y
pérdida de información de clientes. No existe un registro centralizado ni
visibilidad del estado de cada cita.

## Solución

Sistema web accesible desde el browser que centraliza el registro de clientes,
catálogo de servicios y agenda de citas. El negocio puede ver todas las citas
del día, confirmarlas, cancelarlas o marcarlas como completadas desde una sola
interfaz.

## Arquitectura
Browser (HTML + Bootstrap)
↓ HTTP / JSON
Controllers (@RestController)
↓
Services (@Service)
↓
Repositories (JpaRepository)
↓
PostgreSQL 16

---

## Tabla de contenidos

- [Requerimientos](#requerimientos)
- [Instalación](#instalación)
- [Configuración](#configuración)
- [Uso](#uso)
- [Contribución](#contribución)
- [Roadmap](#roadmap)

---

## Requerimientos

### Servidores y bases de datos
- Java 21 (Temurin recomendado)
- Maven 3.9+
- PostgreSQL 16
- Navegador moderno (Chrome, Firefox, Safari)

### Paquetes principales
| Dependencia | Versión | Uso |
|---|---|---|
| Spring Boot | 3.x | Framework principal |
| Spring Data JPA | incluida en Boot | Persistencia |
| PostgreSQL Driver | incluida en Boot | Conexión a BD |
| Lombok | incluida en Boot | Reducción de boilerplate |
| Bootstrap | 5.3.3 | Estilos frontend |
| JUnit 5 | incluida en Boot | Tests unitarios |
| Mockito | incluida en Boot | Mocks para tests |
| H2 | incluida en Boot | BD en memoria para CI |

---

## Instalación

### Ambiente de desarrollo

1. Clona el repositorio:
```bash
git clone git@github.com:konrad-conrad-conrado/schedule-manager-roarnails.git
cd schedule-manager-roarnails
```

2. Instala Java 21 con SDKMAN:
```bash
curl -s "https://get.sdkman.io" | bash
sdk install java 21.0.3-tem
sdk default java 21.0.3-tem
```

3. Instala y levanta PostgreSQL:
```bash
brew install postgresql@16
brew services start postgresql@16
```

4. Crea la base de datos:
```bash
psql postgres
CREATE DATABASE roarnails;
\q
```

5. Corre la aplicación:
```bash
./mvnw spring-boot:run
```

6. Abre en el browser:
   http://localhost:8080/index.html

### Ejecutar pruebas manualmente
```bash
./mvnw test
```

### Implementación en producción (Render)

1. Crea una cuenta en [render.com](https://render.com)
2. Crea un nuevo servicio PostgreSQL en Render y copia la URL de conexión
3. Crea un nuevo Web Service apuntando a este repositorio
4. Agrega las variables de entorno:
   SPRING_DATASOURCE_URL=jdbc:postgresql://...render.com/roarnails
   SPRING_DATASOURCE_USERNAME=tu_usuario
   SPRING_DATASOURCE_PASSWORD=tu_password
5. Render detecta el `pom.xml` y despliega automáticamente

---

## Configuración

### application.properties
```properties
spring.application.name=Schedule_Manager

spring.datasource.url=jdbc:postgresql://localhost:5432/roarnails
spring.datasource.username=tu_usuario_de_mac
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

server.port=8080
```

### Variables de entorno para producción
| Variable | Descripción |
|---|---|
| `SPRING_DATASOURCE_URL` | URL de conexión a PostgreSQL |
| `SPRING_DATASOURCE_USERNAME` | Usuario de la base de datos |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de la base de datos |

---

## Uso

### Usuario final

La interfaz está disponible en `http://localhost:8080/index.html` y tiene
tres secciones:

**Agendar cita**
1. Selecciona un cliente del dropdown
2. Selecciona el servicio deseado
3. Elige fecha y hora
4. Presiona "Agendar cita"

**Registrar cliente nuevo**
1. Ingresa nombre completo (obligatorio)
2. Ingresa teléfono (obligatorio)
3. Ingresa email (opcional)
4. Presiona "Registrar cliente"

**Ver citas del día**
1. Selecciona una fecha en el campo de búsqueda
2. Presiona "Buscar"
3. La tabla muestra hora, cliente, servicio, precio y estado
4. Usa los botones "Confirmar" o "Cancelar" para actualizar el estado

### Usuario administrador

El administrador tiene acceso completo a la API REST para operaciones
avanzadas. Se recomienda usar Postman para administración directa.

**Endpoints disponibles:**

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/clientes` | Listar todos los clientes |
| POST | `/api/clientes` | Crear cliente |
| PUT | `/api/clientes/{id}` | Actualizar cliente |
| DELETE | `/api/clientes/{id}` | Eliminar cliente |
| GET | `/api/servicios` | Listar servicios |
| POST | `/api/servicios` | Crear servicio |
| PUT | `/api/servicios/{id}` | Actualizar servicio |
| DELETE | `/api/servicios/{id}` | Eliminar servicio |
| GET | `/api/citas` | Listar todas las citas |
| GET | `/api/citas/fecha/{fecha}` | Citas por fecha (YYYY-MM-DD) |
| GET | `/api/citas/cliente/{id}` | Citas por cliente |
| POST | `/api/citas` | Crear cita |
| PUT | `/api/citas/{id}/confirmar` | Confirmar cita |
| PUT | `/api/citas/{id}/cancelar` | Cancelar cita |
| PUT | `/api/citas/{id}/completar` | Completar cita |
| DELETE | `/api/citas/{id}` | Eliminar cita |

**Ejemplo de creación de cita via API:**
```json
POST /api/citas
{
    "cliente": { "id": 1 },
    "servicio": { "id": 1 },
    "fecha": "2026-04-15",
    "hora": "14:30:00"
}
```

---

## Contribución

### Guía de contribución

1. Clona el repositorio:
```bash
git clone git@github.com:konrad-conrad-conrado/schedule-manager-roarnails.git
cd schedule-manager-roarnails
```

2. Crea un branch nuevo para tu feature:
```bash
git checkout -b feature/nombre-de-tu-feature
```

3. Realiza tus cambios y haz commit:
```bash
git add .
git commit -m "feat: descripción clara del cambio"
```

4. Sube tu branch:
```bash
git push origin feature/nombre-de-tu-feature
```

5. Abre un Pull Request en GitHub:
   - Ve al repositorio en GitHub
   - Clic en **Compare & pull request**
   - Agrega título y descripción clara
   - Asigna al menos un revisor
   - Clic en **Create pull request**

6. Espera el merge:
   - El revisor aprueba o solicita cambios
   - Una vez aprobado se hace merge a `main`
   - El pipeline de GitHub Actions corre los tests automáticamente

### Convención de commits
feat:     nueva funcionalidad
fix:      corrección de bug
docs:     cambios en documentación
test:     agregar o modificar tests
ci:       cambios en pipeline de CI
refactor: refactorización sin cambio de funcionalidad

---

## Roadmap

### Próximas funcionalidades

- [ ] Autenticación y login para el administrador
- [ ] Notificaciones por WhatsApp al agendar cita (integración Twilio)
- [ ] Integración con Cal.com para booking externo
- [ ] Módulo de reportes — citas por mes, ingresos por servicio
- [ ] Chatbot para agendar citas por WhatsApp e Instagram
- [ ] App móvil para gestión de citas
- [ ] Soporte multi-sucursal

---

## Autor

Conrado Limas — IIS - Tecmilenio  
Actividad 4 — Programación Orientada a Objetos