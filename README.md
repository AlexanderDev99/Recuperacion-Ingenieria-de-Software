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
El **API Gateway** enruta las peticiones directamente a las rutas versionadas `/api/v1`:

| Servicio | Ruta Base (Gateway) | Operaciones Principales |
| :--- | :--- | :--- |
| **Membership** | `/api/v1/memberships` | `POST /`, `GET /create`, `GET /{id}`, `GET /active/{userId}` |
| **Scheduler** | `/api/v1/schedule` | `GET /bookings/create`, `GET /bookings/user/{userId}` |
| **Metrics** | `/api/v1/metrics` | `POST /create`, `GET /user/{userId}` |

## 🔄 Flujo de Negocio Clave
1.  El cliente crea una membresía en `membership-service`.
2.  Al intentar crear una reserva en `scheduler-service`, este valida que el cliente tenga una membresía activa consultando al `membership-service`. **La validación comprueba que `expirationDate > fecha_actual`**.
3.  Si la reserva es exitosa, se dispara un evento `booking.created` hacia RabbitMQ.
4.  El cliente puede registrar métricas de progreso en `metrics-service`, el cual **consulta internamente al `scheduler-service`** para obtener el `classId` del último booking del usuario antes de persistir la información.

### Reglas de Negocio Implementadas
- **Membership - Verificar si está activo:** Compara `expirationDate > LocalDate.now()`. Retorna `true` solo si la fecha de caducidad es mayor a hoy.
- **Scheduler - Último booking:** El endpoint `GET /api/v1/schedule/bookings/user/{userId}` retorna **solo el último booking creado**, ordenado por `createdAt DESC`.
- **Metrics - classId automático:** El `classId` **no se recibe desde el endpoint**; el microservicio consulta al `scheduler-service` para resolverlo.

## 📦 Despliegue y Ejecución
Para levantar todo el ecosistema de servicios, bases de datos y el broker de mensajería, se utiliza Docker Compose:

1.  Asegúrate de tener Docker y Docker Compose instalados.
2.  Desde la raíz del proyecto, ejecuta:
    ```bash
    docker compose up -d --build
    ```
3.  El sistema configurará automáticamente las redes, volúmenes y el orden de arranque mediante healthchecks.
4.  Verifica que todos los servicios estén corriendo:
    ```bash
    docker compose ps
    ```

## 🧪 Manual de Pruebas

### Prerrequisitos
- Todos los servicios deben estar corriendo (`docker compose ps`)
- El API Gateway está disponible en `http://localhost:8080`
- Eureka Dashboard: `http://localhost:8761`

### Flujo Completo de Pruebas

#### Paso 1: Crear una Membresía
```bash
curl -X GET "http://localhost:8080/api/v1/memberships/create?userId=user001&planType=MENSUAL&amountPaid=50.00&paymentMethod=TARJETA&expirationDate=2027-12-31"
```

**Respuesta esperada:**
```json
{
  "id": "uuid-generado",
  "userId": "user001",
  "planType": "MENSUAL",
  "amountPaid": 50.00,
  "paymentMethod": "TARJETA",
  "expirationDate": "2027-12-31"
}
```

#### Paso 2: Verificar Membresía Activa
> **Lógica de negocio:** Retorna `true` solo si `expirationDate > fecha_actual`.

```bash
curl -X GET "http://localhost:8080/api/v1/memberships/active/user001"
```

**Respuesta esperada:** `true`

#### Paso 3: Crear una Reserva (Booking)
> **Nota:** La fecha debe ser futura (año 2027 o posterior). Se valida que el usuario tenga membresía activa.

```bash
curl -X GET "http://localhost:8080/api/v1/schedule/bookings/create?userId=user001&classId=spinning-101&bookDate=2027-03-15T10:00:00&status=ACTIVE"
```

**Respuesta esperada:**
```json
{
  "userId": "user001",
  "classId": "spinning-101",
  "bookingDate": "2027-03-15T10:00:00"
}
```

#### Paso 4: Consultar Último Booking del Usuario
> **Lógica de negocio:** Retorna **solo el último booking creado**, no una lista completa.

```bash
curl -X GET "http://localhost:8080/api/v1/schedule/bookings/user/user001"
```

**Respuesta esperada:** Solo el último booking creado (no una lista).

#### Paso 5: Registrar una Métrica
> **Lógica de negocio:** El `classId` se obtiene automáticamente consultando al `scheduler-service`, no se envía en el request.

```bash
curl -X POST "http://localhost:8080/api/v1/metrics/create" \
  -H "Content-Type: application/json" \
  -d '{"userId":"user001","exercise":"Deadlift","value":120.5,"unit":"kg"}'
```

**Respuesta esperada:**
```json
{
  "id": "mongo-object-id",
  "userId": "user001",
  "exercise": "Deadlift",
  "value": 120.5,
  "unit": "kg",
  "classId": "spinning-101",
  "timestamp": "2026-02-20T..."
}
```

#### Paso 6: Consultar Métricas del Usuario
```bash
curl -X GET "http://localhost:8080/api/v1/metrics/user/user001"
```

### Validaciones Implementadas

| Servicio | Campo | Validación |
|----------|-------|------------|
| Membership | `userId` | `@NotBlank` - Obligatorio |
| Membership | `planType` | `@NotNull` - Obligatorio |
| Membership | `amountPaid` | `@Positive` - Mayor a cero |
| Scheduler | `bookingDate` | Debe ser fecha futura |
| Scheduler | `userId` | Membresía debe estar activa |
| Metrics | `userId` | `@NotBlank` - Obligatorio |
| Metrics | `exercise` | `@NotBlank` - Obligatorio |
| Metrics | `value` | `@Positive` - Mayor a cero |
| Metrics | `classId` | Se obtiene del Scheduler (no se envía) |

### Puertos de los Servicios

| Servicio | Puerto |
|----------|--------|
| API Gateway | 8080 |
| Eureka | 8761 |
| Membership | 8081 |
| Scheduler | 8082 |
| Metrics | 8083 |
| PostgreSQL | 5433 |
| MongoDB (Scheduler) | 27017 |
| MongoDB (Metrics) | 27018 |
| RabbitMQ | 5672 / 15672 |

---
*Este proyecto se desarrolla bajo la metodología Extreme Programming (XP), priorizando la comunicación constante, la simplicidad en el diseño, la retroalimentación rápida a través de pruebas automatizadas y entregas frecuentes de software funcional.*