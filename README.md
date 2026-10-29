# Secure IAM API

A RESTful Identity and Access Management (IAM) API built with Java and Spring Boot, featuring JWT-based authentication, BCrypt password hashing, and role-based access control (RBAC).

## Overview

This project implements the core authentication and authorization features required by a secure backend application. It allows users to register, log in, access protected endpoints, and enforce role-based restrictions on administrative resources.

## Features

- **User Registration:** Register accounts with email and username validation.
- **Secure Password Storage:** Hash passwords using BCrypt.
- **JWT Authentication:** Generate and validate JSON Web Tokens for authenticated requests.
- **Role-Based Access Control (RBAC):** Separate `USER` and `ADMIN` permissions.
- **Protected REST Endpoints:** Restrict access to authenticated users.
- **Admin Authorization:** Protect administrative endpoints using role-based rules.
- **MySQL Integration:** Persist user information using Spring Data JPA and Hibernate.
- **Stateless Authentication:** Authenticate requests using bearer tokens.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java | Backend development |
| Spring Boot | REST API framework |
| Spring Security | Authentication and authorization |
| JWT (JJWT) | Token generation and validation |
| BCrypt | Password hashing |
| Spring Data JPA | Database persistence |
| MySQL | Relational database |
| Maven | Dependency management and build automation |
| Postman | API testing |

## Architecture

```text
Client / Postman
       |
       v
 Spring Boot REST API
       |
       v
 Spring Security Filter Chain
       |
       v
 JWT Authentication Filter
       |
       v
 Authentication / Authorization
       |
       v
 Controllers -> Services -> Repositories
                              |
                              v
                            MySQL
```

## API Endpoints

Base URL: `http://localhost:8080`

| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/auth/register` | Public | Register a new user |
| POST | `/api/auth/login` | Public | Authenticate and receive a JWT |
| GET | `/api/auth/me` | Authenticated | Access the current user's protected endpoint |
| GET | `/api/admin/dashboard` | ADMIN only | Access the admin dashboard |
| GET | `/api/admin/users` | ADMIN only | List all registered users |

### Authentication Flow

1. A user registers with a username, email, and password.
2. The application hashes the password using BCrypt before storing it.
3. The user logs in with their credentials.
4. Spring Security authenticates the credentials.
5. The API returns a signed JWT.
6. The client sends the token in the `Authorization` header for protected requests.
7. The JWT filter validates the token and establishes the authenticated security context.
8. Spring Security checks the user's role before granting access to administrative endpoints.

### Example Authorization Header

```http
Authorization: Bearer <your-jwt-token>
```

## Getting Started

### Prerequisites

- Java version compatible with the project configuration
- MySQL Server
- Git
- Maven, or the included Maven Wrapper

### 1. Clone the Repository

```bash
git clone https://github.com/14shreyas04/secure-iam-api.git
cd secure-iam-api
```

### 2. Configure the Database

Create a MySQL database:

```sql
CREATE DATABASE project;
```

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

jwt.secret=${JWT_SECRET}
jwt.expiration-ms=3600000
```

Set `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, and `JWT_SECRET` in your local environment before starting the application. The JWT secret must be a securely generated Base64-encoded key of sufficient length for the configured signing algorithm.

### 3. Build the Project

On Windows PowerShell:

```powershell
.\mvnw.cmd clean test
```

On Linux or macOS:

```bash
./mvnw clean test
```

### 4. Run the Application

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

On Linux or macOS:

```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`, provided the configuration is correct and the port is available.

## Testing with Postman

### Register a User

`POST /api/auth/register`

Example request body (adjust field names to match your DTO):

```json
{
  "username": "testuser",
  "email": "test@example.com",
  "password": "ChangeThisExamplePassword123!"
}
```

### Log In

`POST /api/auth/login`

Example request body:

```json
{
  "email": "test@example.com",
  "password": "ChangeThisExamplePassword123!"
}
```

Copy the returned JWT and use it in the Authorization tab in Postman, selecting **Bearer Token**.

### Test Access Control

- A valid token for a `USER` should allow access to `/api/auth/me`.
- A valid token for a `USER` should be denied access to `/api/admin/dashboard`.
- A request without a valid token should be denied access to protected endpoints.
- An account with the `ADMIN` role should be allowed to access the admin dashboard.

## Security Considerations

- Never store plaintext passwords.
- Never commit JWT signing secrets, database passwords, access tokens, or refresh tokens.
- Do not allow users to assign themselves administrative roles through public registration.
- Validate JWT signatures and expiration.
- Apply least-privilege authorization rules to protected endpoints.
- Use HTTPS when deploying outside a local development environment.
- Use dedicated database credentials with appropriate privileges.

## Future Improvements

- Refresh token lifecycle and revocation.
- Logout and session invalidation.
- Automated security and integration tests.
- API documentation with OpenAPI/Swagger.
- Rate limiting and brute-force protection.
- Containerized deployment and CI/CD.
