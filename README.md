PaySphere – Payment & Transaction Management System
Project Overview
PaySphere is a secure payment and transaction management system designed to handle financial transactions between users. The system allows users to create transactions, view transaction history, manage accounts, and generate transaction reports.
This project demonstrates the implementation of full-stack development using Java Spring Boot and Angular.

Technologies Used
Backend
•	Java 8
•	Spring Boot
•	Spring Data JPA / Hibernate
•	REST APIs
•	MySQL  Database
Frontend
•	Angular 18
•	HTML5
•	CSS3
•	Bootstrap / Angular Material
Tools
•	IntelliJ IDEA
•	GitHub
•	Maven
•	Postman

Features
User Management
•	User registration
•	Login authentication
•	Forgot password functionality
•	Role-based access
Transaction Management
•	Create transaction
•	Update transaction
•	View transaction details
•	Delete transaction
•	Transaction history
Reports
•	Transaction reports
•	Export reports in PDF
•	Export reports in Excel
•	Filter by date and status
Security
•	JWT authentication
•	Input validation
•	Global exception handling

System Architecture
Angular Frontend
        |
        |
REST API Communication
        |
Spring Boot Backend
        |
Service Layer (Business Logic)
        |
Repository Layer (JPA/Hibernate)
        |
Database (MySQL)

## Screenshots

### Login Page
![Login Page](images/login_page.png)

### Transaction List
![Transaction List](images/transaction_list.png)

### User List
![User List](images/User_list.png)

### Create User
![Create User](images/create_user.png)

### Update User
![Update User](images/update_user.png)

### Forgot Password
![Forgot Password](images/forgot_password.png)

### Reset Password
![Reset Password](images/reset_password.png)

## 📡 API Endpoints

### 🔐 Authentication APIs

| Method | Endpoint | Description |
|--------|---------|------------|
| POST | /auth/login | Authenticate user and return JWT token |
| POST | /auth/forgot-password | Send reset password link to email |
| POST | /auth/reset-password | Reset password using token |

### 👤 User Management APIs

| Method | Endpoint | Description |
|--------|---------|------------|
| POST | /api/users/create | Create new user (with photo upload) |
| GET | /api/users | Get all users (with pagination & filters) |
| GET | /api/users/{id} | Get user by ID |
| PUT | /api/users/{id} | Update user (with photo upload) |
| DELETE | /api/users/{id} | Delete user |
---

### 🔍 Query Parameters (GET /api/users)

| Parameter | Type | Description |
|----------|------|------------|
| page | int | Page number (default: 0) |
| size | int | Page size (default: 5) |
| username | String | Filter by username |
| role | String | Filter by role |
| status | String | Filter by status |


How to Run the Project
Backend
Clone repository
git clone https://github.com/yourusername/paysphere.git
Open project in IntelliJ / STS
Configure database in application.properties
Run Spring Boot application
Frontend
Navigate to Angular project
cd paysphere-frontend
Install dependencies
npm install
Application run on
http://localhost:4200

Future Enhancements
Payment gateway integration
Microservices architecture
Kafka event streaming
Real-time notifications
Cloud deployment (AWS / Docker)

Author
Bhagyashri Sayankar

Java Backend Developer
Skills: Java, Spring Boot, Hibernate, Angular,
