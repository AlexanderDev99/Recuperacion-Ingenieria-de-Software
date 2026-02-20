# FitFlow - Sistema de Gestión Fitness (Arquitectura de Microservicios)

FitFlow es un backend organizado como un **monorepo** desarrollado en **Java/Spring Boot**, diseñado bajo una arquitectura de microservicios para la gestión de servicios fitness.

## 🚀 Descripción del Proyecto
El sistema se compone de cuatro microservicios de negocio y una infraestructura de soporte para el descubrimiento y enrutamiento de servicios. El alcance técnico se centra en operaciones de creación (**POST**) y lectura (**GET**) por cada dominio, con bases de datos dedicadas para asegurar el aislamiento.

### Componentes de la Plataforma
*   **Discovery (Eureka Server):** Registro y descubrimiento interno de microservicios.
*   **API Gateway (Spring Cloud Gateway):** Punto de entrada único que gestiona las rutas por prefijo.
*   **RabbitMQ:** Broker de eventos para la comunicación asíncrona (productor de alertas).
*   **Bases de Datos:** PostgreSQL para la gestión de membresías y MongoDB para reservas y métricas.

## 🏗️ Arquitectura de Microservicios
1.  **membership-service:** Gestión de membresías y estados de cuenta (PostgreSQL).
2.  **scheduler-service:** Gestión de reservas y horarios (MongoDB). Incluye el productor de alertas.
3.  **metrics-service:** Registro de progreso físico y marcas personales (MongoDB).

## 🛠️ Tecnologías Utilizadas
*   **Lenguaje:** Java con Spring Boot.
*   **Infraestructura:** Spring Cloud (Eureka, Gateway).
*   **Mensajería:** RabbitMQ.
*   **Persistencia:** SQL (PostgreSQL) y NoSQL (MongoDB).
*   **Contenerización:** Docker y Docker Compose.

## 🚦 Endpoints Principales
El **API Gateway** enruta las peticiones de la siguiente manera:

| Servicio | Ruta Base (Gateway) | Operaciones Principales |
| :--- | :--- | :--- |
| **Membership** | `/membership/**` | `POST /create`, `GET /{id}`, `GET /active/{userId}` |
| **Scheduler** | `/scheduler/**` | `POST /bookings/create`, `GET /bookings/user/{userId}` |
| **Metrics** | `/metrics/**` | `POST /create`, `GET /user/{userId}` |

## 🔄 Flujo de Negocio Clave
1.  El cliente crea una membresía en `membership-service`.
2.  Al intentar crear una reserva en `scheduler-service`, este valida que el cliente tenga una membresía activa consultando al `membership-service`.
3.  Si la reserva es exitosa, se dispara un evento `booking.created` hacia RabbitMQ.
4.  El cliente puede registrar métricas de progreso en `metrics-service`, el cual valida el contexto de la clase con `scheduler-service` antes de persistir la información.

## 📦 Despliegue y Ejecución
Para levantar todo el ecosistema de servicios, bases de datos y el broker de mensajería, se utiliza Docker Compose:

1.  Asegúrate de tener Docker y Docker Compose instalados.
2.  Desde la raíz del proyecto, ejecuta:
    ```bash
    docker compose up -d
    ```
3.  El sistema configurará automáticamente las redes, volúmenes y el orden de arranque mediante healthchecks.

---
*Este proyecto se desarrolla bajo la metodología Extreme Programming (XP), priorizando la comunicación constante, la simplicidad en el diseño, la retroalimentación rápida a través de pruebas automatizadas y entregas frecuentes de software funcional.*