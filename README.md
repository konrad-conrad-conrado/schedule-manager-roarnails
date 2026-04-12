# ROAR NAILS — Gestor de Citas

Sistema de gestión de citas para un salón de uñas, desarrollado como proyecto integrador
de la materia de Programación Orientada a Objetos en Tecmilenio.

## Descripción

Aplicación web que permite registrar clientes, definir servicios y agendar citas.
Incluye vista de citas por día con opciones para confirmar o cancelar.

## Conceptos OOP Aplicados

### Encapsulación
Todos los modelos (`Cliente`, `Servicio`, `Cita`) tienen sus campos privados
y acceso controlado a través de getters y setters generados por Lombok.

### Composición
`Cita` está compuesta por objetos `Cliente` y `Servicio` — una cita no es un
cliente ni un servicio, pero los contiene como parte de su estado.

### Herencia implícita
Spring Data JPA permite que los repositorios hereden operaciones CRUD completas
simplemente extendiendo `JpaRepository`, sin escribir implementaciones manuales.

### Separación de responsabilidades
Cada capa tiene una responsabilidad clara:
- `model` — representa los datos y la estructura de la base de datos
- `repository` — acceso y persistencia de datos con PostgreSQL
- `service` — lógica de negocio y reglas de la aplicación
- `controller` — manejo de requests HTTP y respuestas REST

## Tecnologías

| Tecnología       | Uso                        |
|------------------|----------------------------|
| Java 21          | Lenguaje principal         |
| Spring Boot 3    | Framework backend          |
| Spring Data JPA  | Persistencia de datos      |
| PostgreSQL 16    | Base de datos              |
| Lombok           | Reducción de boilerplate   |
| Bootstrap 5      | Estilos del frontend       |
| HTML + JS        | Interfaz de usuario        |
| Maven            | Gestión de dependencias    |

## Estructura del Proyecto

src/
└── main/
├── java/com/roarnails/schedule_manager/
│   ├── model/
│   │   ├── Cliente.java
│   │   ├── Servicio.java
│   │   └── Cita.java
│   ├── repository/
│   │   ├── ClienteRepository.java
│   │   ├── ServicioRepository.java
│   │   └── CitaRepository.java
│   ├── service/
│   │   ├── ClienteService.java
│   │   ├── ServicioService.java
│   │   └── CitaService.java
│   ├── controller/
│   │   ├── ClienteController.java
│   │   ├── ServicioController.java
│   │   └── CitaController.java
│   ├── CorsConfig.java
│   └── ScheduleManagerApplication.java
└── resources/
├── static/
│   └── index.html
└── application.properties

## Endpoints REST

### Clientes
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/clientes` | Listar todos los clientes |
| GET | `/api/clientes/{id}` | Obtener cliente por ID |
| POST | `/api/clientes` | Crear nuevo cliente |
| PUT | `/api/clientes/{id}` | Actualizar cliente |
| DELETE | `/api/clientes/{id}` | Eliminar cliente |

### Servicios
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/servicios` | Listar todos los servicios |
| GET | `/api/servicios/{id}` | Obtener servicio por ID |
| POST | `/api/servicios` | Crear nuevo servicio |
| PUT | `/api/servicios/{id}` | Actualizar servicio |
| DELETE | `/api/servicios/{id}` | Eliminar servicio |

### Citas
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/citas` | Listar todas las citas |
| GET | `/api/citas/{id}` | Obtener cita por ID |
| GET | `/api/citas/fecha/{fecha}` | Citas por fecha (YYYY-MM-DD) |
| GET | `/api/citas/cliente/{id}` | Citas por cliente |
| POST | `/api/citas` | Crear nueva cita |
| PUT | `/api/citas/{id}/confirmar` | Confirmar cita |
| PUT | `/api/citas/{id}/cancelar` | Cancelar cita |
| PUT | `/api/citas/{id}/completar` | Completar cita |
| DELETE | `/api/citas/{id}` | Eliminar cita |

## Cómo correrlo

### Requisitos
- Java 21+
- PostgreSQL 16
- Maven

### Configuración
1. Crear la base de datos en PostgreSQL:
```sql
CREATE DATABASE roarnails;
```

2. Actualizar `application.properties` con tu usuario de sistema:
```properties
spring.datasource.username=tu_usuario
```

3. Correr la aplicación desde IntelliJ o con:
```bash
./mvnw spring-boot:run
```

4. Abrir en el browser:
   http://localhost:8080/index.html

## Autor
Conrado Limas — Tecmilenio  IIS
Proyecto final — Taller de productividad basada en herramientas tecnológicas