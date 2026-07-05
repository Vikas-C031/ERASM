# Enterprise Resource Allocation & Skill Management System (ERASM)

## 📌 Project Overview

The Enterprise Resource Allocation & Skill Management System (ERASM) is a secure Spring Boot REST API designed to streamline employee skill management, project allocation, and resource planning within an enterprise.

The system enables organizations to manage employees, projects, skills, certifications, resource requests, allocations, authentication, audit logs, dashboards, and reports through a role-based access control system secured using JWT authentication.

---

## 🎯 Objectives

- Centralized Employee Management
- Skill Inventory Management
- Certification Tracking
- Project Management
- Resource Request Workflow
- Employee Allocation & Utilization Tracking
- Dashboard & Analytics
- Report Generation
- Audit Logging
- Secure Authentication & Authorization

---

## ✨ Features

### Authentication & Security
- JWT Authentication
- User Registration
- Login
- Change Password
- Role-Based Authorization (RBAC)

### Employee Management
- Employee CRUD
- Employee Search
- Employee Profile Management

### Skill Management
- Skill CRUD
- Employee Skill Mapping
- Skill Level Management

### Certification Management
- Add Certifications
- Update Certifications
- View Certifications
- Delete Certifications

### Project Management
- Project CRUD
- Project Search
- Project Status Management

### Resource Request Management
- Create Resource Requests
- Submit Request
- Review Request
- Approve Request
- Reject Request
- Allocate Request
- Complete Request

### Allocation Management
- Employee Allocation
- Release Allocation
- Reallocate Employee
- Allocation Percentage Validation
- Bench Identification

### Dashboard
- Employee Utilization
- Bench Employees
- Project Allocations
- Dashboard Summary

### Reports
- Skill Inventory Report
- Project Summary Report
- Resource Request Report

### Audit Logs
- Create Audit Logs
- View Audit Logs
- Filter by Action
- Filter by Entity

---

# 🛠 Technology Stack

| Technology | Version |
|------------|----------|
| Java | 17 |
| Spring Boot | 3.5.x |
| Spring Security | 6.x |
| Spring Data JPA | Latest |
| Hibernate | Latest |
| MySQL | 8.x |
| Maven | 3.x |
| JWT | JJWT 0.12.7 |
| Lombok | Latest |
| JUnit 5 | Testing |
| Mockito | Testing |

---

# 🏗 Architecture

```
Controller
      ↓
Service
      ↓
Repository
      ↓
MySQL Database
```

The project follows a layered architecture:

- Controller Layer
- Service Layer
- Repository Layer
- Entity Layer
- DTO Layer
- Mapper Layer
- Security Layer

---

# 📂 Modules

- Authentication
- User Management
- Role Management
- Employee Management
- Skill Management
- Employee Skill Management
- Certification Management
- Project Management
- Resource Request Management
- Allocation Management
- Dashboard
- Reports
- Audit Logs

---

# 🔐 User Roles

- ADMIN
- DELIVERY_MANAGER
- RESOURCE_MANAGER
- EMPLOYEE
- AUDITOR

---

# 📊 Reports

- Skill Inventory Report
- Project Summary Report
- Resource Request Report

---

# 📈 Dashboard

- Total Employees
- Allocated Employees
- Bench Employees
- Employee Utilization
- Project Allocation Summary

---

# 📁 Project Structure

```
src/
docs/
postman/
sql/
logs/
pom.xml
README.md
```

---

# 🚀 API Testing

All REST APIs were tested using **Postman**.

The project includes:

- ERASM API Collection
- SQL Database Script
- JWT Authentication
- Sample Test Data

---

# 🗄 Database

Database: **MySQL**

The SQL script is available in:

```
sql/erasm.sql
```

---

# ▶ Running the Project

### Clone Repository

```bash
git clone https://github.com/Vikas-C031/ERASM.git
```

### Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/erasm_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### Run

```bash
mvn spring-boot:run
```

---

# 📬 API Documentation

Import the Postman collection located in:

```
postman/ERASM API Collection.postman_collection.json
```

Authenticate using the Login API to obtain a JWT token before accessing secured endpoints.

---

# 📌 Project Status

| Module | Status |
|---------|--------|
| Authentication | ✅ |
| User Management | ✅ |
| Role Management | ✅ |
| Employee Management | ✅ |
| Skill Management | ✅ |
| Employee Skill Management | ✅ |
| Certification Management | ✅ |
| Project Management | ✅ |
| Resource Request Workflow | ✅ |
| Allocation Management | ✅ |
| Dashboard | ✅ |
| Reports | ✅ |
| Audit Logs | ✅ |
| API Testing | ✅ |
| Documentation | ✅ |
| JUnit Testing | 🚧 In Progress |

---

# 👨‍💻 Author

**Vikas C**

Computer Science Engineering  
Vidyavardhaka College of Engineering

---

# 📄 License

This project was developed as part of the **ERASM Enterprise Resource Allocation & Skill Management System** academic project.