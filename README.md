# Finance Backend System

A scalable backend system built using **Spring Boot** that provides financial record management, role-based access control, and dashboard analytics.

---

## Features

* JWT Authentication & Authorization
* Role-Based Access Control (RBAC)

  * Admin
  * Analyst
  * Viewer
* Financial Records Management (CRUD)
* Dashboard Summary APIs
* Filtering (date, category, type)
* Pagination & Sorting
* Validation & Exception Handling
* Swagger API Documentation
* Structured Logging (SLF4J)

---

## Tech Stack

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA (Hibernate)
* MySQL
* JWT (jjwt)
* Swagger (Springdoc OpenAPI)
* Maven

---

## Setup Instructions

### 1 Clone Repository

```bash
git clone https://github.com/your-username/finance-backend-design.git
cd finance-backend-design
```

---

### 2 Configure Database

Update `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/finance_db
    username: root
    password: root
```

---

### 3Run Application

```bash
mvn clean install
mvn spring-boot:run
```

---

### 4️ Access APIs

* Swagger UI:
  http://localhost:8080/swagger-ui/index.html

---

##  Authentication Flow

1. Login using:

```http
POST /auth/login
```

2. Get JWT token

3. Use token in headers:

```text
Authorization: Bearer <token>
```

---

## Role-Based Access

| Role    | Permissions                               |
| ------- | ----------------------------------------- |
| ADMIN   | Full access (users + records + dashboard) |
| ANALYST | Records + dashboard                       |
| VIEWER  | Dashboard only                            |

---

## API Overview

###  Auth

* `POST /auth/login` → Login and get token

---

### Users (ADMIN only)

* `POST /users` → Create user
* `GET /users` → Get all users (pagination)
* `PATCH /users/{id}/role` → Update role
* `PATCH /users/{id}/status` → Activate/Deactivate user
* `DELETE /users/{id}` → Delete user

---

### Financial Records

* `POST /records` → Create record
* `GET /records` → Get records (pagination)
* `PUT /records/{id}` → Update record
* `DELETE /records/{id}` → Delete record

#### Filters:

* `/records/type?type=INCOME`
* `/records/category?category=Food`
* `/records/date?start=2026-01-01&end=2026-12-31`

---

### Dashboard

* `GET /dashboard/summary`

  * Total Income
  * Total Expense
  * Net Balance
  * Category-wise totals
  * Recent transactions

---

## Pagination Example

```http
GET /records?page=0&size=5&sort=date,desc
```

---

## Assumptions

* Each financial record belongs to a user (via `userId`)
* Roles are predefined (ADMIN, ANALYST, VIEWER)
* JWT token is stateless and used for all secured endpoints
* Default admin user is created at application startup

---

## Trade-offs

* Used **simple JWT authentication
* Used **basic role checks
* Used **MySQL relational DB
* Stored `userId`

---

## Future Improvements

* BCrypt password encryption
* Advanced analytics (monthly trends, graphs)
* Deployment (Docker / AWS)

---

