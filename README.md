# 🚀 Project Advantages

> This pet project showcases a **robust** and **secure** Spring Boot application with several key advantages.

---

## 🔒 Enhanced Security Features

> The application implements advanced security measures beyond basic authentication. This includes:

- **🔑 JWT-based Authentication**  
  Securely authenticates users using JSON Web Tokens, providing a **stateless** and **scalable** authentication mechanism.

- **🚫 Account Locking with Failed Attempt Tracking**  
  Protects against brute-force attacks by locking user accounts after a configurable number of failed login attempts, preventing unauthorized access.

- **🤖 reCAPTCHA Integration**  
  Integrates Google reCAPTCHA to differentiate between human users and bots, adding an extra layer of security to **registration** and **login** processes.

- **🔐 Password Hashing (BCrypt)**  
  Stores user passwords securely using **BCrypt hashing**, protecting sensitive information even in the event of a data breach.

---

## 🛡️ Robust Authentication and Authorization

> The project features a well-defined and flexible security setup:

- **🧩 Custom JWT Filter**  
  A dedicated `JwtAuthFilter` efficiently intercepts requests, validates JWT tokens, and sets up the Spring Security context.

- **👥 Role-Based Access Control (RBAC)**  
  Leverages Spring Security's capabilities to implement role-based authorization, ensuring users can only access resources aligned with their assigned roles (e.g., `ROLE_USER`, `ROLE_ADMIN`).

- **🌐 Stateless Session Management**  
  Configured for **stateless sessions**, making the application highly scalable and suitable for **distributed environments** by not storing session state on the server.

---

## 🧱 Clean Architecture and Maintainability

> The codebase is structured for clarity and ease of maintenance:

- **📚 Layered Architecture**  
  Follows a standard layered structure: `Controller`, `Service`, `Repository`, `Entity`, `DTO` — ensuring clear **separation of concerns**, which makes the codebase easy to understand, test, and extend.

- **📦 Data Transfer Objects (DTOs)**  
  Uses DTOs to safely transfer data between layers, exposing and validating only the necessary information.

- **⚠️ Custom Exception Handling**  
  Incorporates custom exceptions to provide meaningful error messages and better control over error flows.

- **📝 Comprehensive Javadoc and Inline Comments**  
  The code is well-documented with Javadoc and concise inline comments to **enhance readability** and support future development.

---

## 🧪 Practical Demonstrations

> This project serves as a real-world example of implementing secure features using Spring Boot and Spring Security:

- **👤 User Registration and Login Flow**  
  Demonstrates a full flow including validation, password encoding, and JWT token generation.

- **🍪 Cookie-Based JWT Management**  
  Shows how to store JWT tokens securely in **HttpOnly cookies**, improving protection against XSS attacks.

- **⚙️ Configuration Best Practices**  
  Utilizes `@Value` annotations and external configuration files for environment flexibility without touching the codebase.


