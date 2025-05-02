# Sales Management System

A full-stack Sales Management System built with Spring Boot microservices (Java 19), Angular 19 frontend, Redis caching, OpenFeign for inter-service communication, secured by JWT, and deployed using Docker Compose (with optional Docker Swarm).

---

## 🧩 Project Structure

```
sales-management-system/
│
├── auth-service/           # Handles login/registration, JWT auth
├── user-service/           # Manages users (CRUD)
├── order-service/          # Manages orders (CRUD)
├── Sales-Application-Frontend/  # Angular 19 frontend
├── docker-compose.yml
├── .env
└── README.md
```

---

## 🧠 Tech Stack

### Backend
- **Spring Boot 3.4.5**
- **Java 19**
- **Spring Security + JWT**
- **Spring Data JPA + PostgreSQL**
- **Spring Web + Spring Validation**
- **OpenFeign** (inter-service communication)
- **Redis** (caching)
- **Swagger/OpenAPI 3**

### Frontend
- **Angular 19 (Standalone Components)**
- **Bootstrap + SCSS styling**
- **JWT Interceptor + AuthGuard**
- **Reactive Forms, Angular Routing**

### Deployment
- **Docker & Docker Compose**
- **Docker Swarm ready (optional configuration)**

---

## ⚙️ Backend Microservices

| Service        | Port | Description |
|----------------|------|-------------|
| `auth-service` | 8081 | Login, Register, JWT |
| `user-service` | 8083 | User management |
| `order-service`| 8082 | Order management |

### API Endpoints

Each service exposes REST endpoints, documented using Swagger.

| Service        | Swagger UI                            |
|----------------|----------------------------------------|
| Auth Service   | `http://localhost:8081/swagger-ui/swagger-ui/index.html` |
| User Service   | `http://localhost:8083/swagger-ui/swagger-ui/index.html` |
| Order Service  | `http://localhost:8082/swagger-ui/swagger-ui/index.html` |

---

## 🌐 Frontend App

| URL                    | Description        |
|------------------------|--------------------|
| `http://localhost:4200` | Angular UI         |

- Login/Register (auth-service)
- User List / Add / Edit / Delete (user-service)
- Order List / Add / Edit / Delete (order-service)

---

## 🔐 JWT Authentication Flow

- On login, `auth-service` returns a JWT token
- Token is stored in `localStorage` on the frontend
- Angular uses `TokenInterceptor` to attach token to all outgoing API requests
- `AuthGuard` restricts routes based on login state

---

## 🐳 Docker Setup

### Prerequisites

- Docker Desktop
- Docker Compose
- (Optional) Docker Swarm initialized (`docker swarm init`)

### 🧪 Local Development (Docker Compose)

1. Clone the repo

```bash
git clone https://github.com/your-username/sales-management-system.git
cd sales-management-system
```

2. Build and run services

```bash
docker-compose up --build
```

3. Access the services:
   - Frontend: `http://localhost:4200`
   - Swagger UIs (see ports above)

### 🐝 Docker Swarm (Optional Production Deployment)

> ⚠️ You can skip this if using only `docker-compose`

1. Initialize swarm:

```bash
docker swarm init
```

2. Deploy stack:

```bash
docker stack deploy -c docker-compose.yml sales-system
```

3. Check status:

```bash
docker service ls
```

---

## 🧰 Redis Caching

- Redis is used by `user-service` and `order-service` for faster reads
- Runs on default port `6379` via Docker container
- TTL and cache invalidation managed using Spring Cache abstraction

---

## 🌉 OpenFeign

Used for internal REST API communication between microservices. For example:

- `auth-service` calls `user-service` to verify user by email before creating order
- Authenticated requests are forwarded with token headers internally





## 👨‍💻 Contributors

- **Developer**: Mazhar Hussain  
- **Stack**: Java 19, Spring Boot 3.4.5, Angular 19, Docker

---

