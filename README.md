Project Advantages
This pet project showcases a robust and secure Spring Boot application with several key advantages:

Enhanced Security Features
The application implements advanced security measures beyond basic authentication, including:

JWT-based Authentication: Securely authenticates users using JSON Web Tokens, providing a stateless and scalable authentication mechanism.
Account Locking with Failed Attempt Tracking: Protects against brute-force attacks by locking user accounts after a configurable number of failed login attempts.
reCAPTCHA Integration: Integrates Google reCAPTCHA to differentiate between human users and bots, adding an extra layer of security to registration and login processes.
Password Hashing (BCrypt): Stores user passwords securely using BCrypt hashing, protecting sensitive information even in the event of a data breach.
Robust Authentication and Authorization
The project features a well-defined and flexible security setup:

Custom JWT Filter: A dedicated JwtAuthFilter efficiently intercepts requests, validates JWT tokens, and sets up the Spring Security context.
Role-Based Access Control (RBAC): Leverages Spring Security's capabilities to implement role-based authorization, ensuring users can only access resources aligned with their assigned roles (e.g., ROLE_USER, ROLE_ADMIN).
Stateless Session Management: Configured for stateless sessions, making the application highly scalable and suitable for distributed environments.
Clean Architecture and Maintainability
The codebase is structured for clarity and ease of maintenance:

Layered Architecture: Follows a standard layered architecture (Controller, Service, Repository, Entity, DTO) for clear separation of concerns.
Data Transfer Objects (DTOs): Uses DTOs for data transfer between layers, ensuring that only necessary data is exposed and validated.
Custom Exception Handling: Incorporates custom exceptions to provide meaningful error messages and better control over error flows.
Comprehensive Javadoc and Inline Comments: The code is well-documented with Javadoc for methods and concise inline comments, enhancing readability.
Practical Demonstrations
This project serves as an excellent example of implementing various Spring Boot and Spring Security features:

User Registration and Login Flow: Demonstrates a complete user registration and login flow, including validation, password encoding, and token generation.
Cookie-based JWT Management: Shows how to handle JWT tokens efficiently by storing them in HttpOnly cookies, improving client-side security.
Configuration Best Practices: Utilizes @Value annotations for externalizing configuration, making the application adaptable to different environments without code changes.
