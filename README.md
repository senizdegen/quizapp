# 🧠 QuizApp

**QuizApp** is a full-featured web application for creating, managing, and taking quizzes.  
It is built with **Spring Boot** and secured with **JWT-based authentication**.  
Users can register, log in, and interact with quizzes and questions based on their roles.

---

## 🚀 Features

- ✅ User registration and login with JWT security
- 🧩 Create, update, delete quizzes and questions
- 👤 Role-based access control
- 📚 Categorize questions by difficulty and topic
- 🔐 Secure endpoints for authorized users
- 🧪 Submit answers and get quiz results
- 📖 RESTful API documentation via Swagger

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Security + JWT**
- **Spring Data JPA + Hibernate**
- **PostgreSQL**
- **Lombok**
- **Swagger/OpenAPI**
- **Maven**
- **IntelliJ IDEA**

---

## 📌 API Endpoints

### 🔐 Auth Endpoints

| Method | Endpoint         | Description        |
|--------|------------------|--------------------|
| POST   | `/auth/sign-up`  | Register new user  |
| POST   | `/auth/sign-in`  | Login and get JWT  |

### 📋 Quiz Controller

| Method | Endpoint              | Description                    |
|--------|-----------------------|--------------------------------|
| GET    | `/quiz/{id}`          | Get quiz by ID (🌐 Public)      |
| PUT    | `/quiz/{id}`          | Update quiz by ID (🔒 Auth)     |
| DELETE | `/quiz/{id}`          | Delete quiz by ID (🔒 Auth)     |
| GET    | `/quiz`               | Get all quizzes (🌐 Public)     |
| POST   | `/quiz`               | Create a new quiz (🔒 Auth)     |
| POST   | `/quiz/submit/{id}`   | Submit quiz answers (🔒 Auth)   |

### ❓ Question Controller

| Method | Endpoint                        | Description                        |
|--------|----------------------------------|------------------------------------|
| GET    | `/question/{id}`                | Get question by ID (🔒 Auth)       |
| PUT    | `/question/{id}`                | Update question (🔒 Auth)          |
| DELETE | `/question/{id}`                | Delete question (🔒 Auth)          |
| GET    | `/question`                     | Get all questions (🔒 Auth)        |
| POST   | `/question`                     | Create a question (🔒 Auth)        |
| GET    | `/question/category/{category}` | Get questions by category (🔒 Auth)|

> 🔒 Auth — requires JWT token  
> 🌐 Public — available without authentication

---

## ✅ Getting Started

### 📦 Prerequisites

- Java 17+
- Maven
- IntelliJ IDEA (or any Java IDE)
- Docker (optional, for PostgreSQL)

### 🚴‍♀️ Run Locally

```bash
git clone https://github.com/your-username/quizapp.git
cd quizapp
./mvnw spring-boot:run
```

---

## 🔐 JWT Authentication

After logging in via `/auth/sign-in`, you will receive a JWT token.

To authorize Swagger requests:

1. Click the **Authorize** button in Swagger UI.
2. Enter the token as:
```
Bearer your_jwt_token
```

---

## 🌱 Future Improvements

- 🎨 Frontend with React or Angular
- 📧 Email verification, password reset, auth with google and github
- 🛠 Admin dashboard for managing users
- 📊 Quiz analytics for results and progress

---


## ✨ Contact

Made by [Dosymzhan](https://github.com/senizdegen)
Email: seisendosymzhan05@gmail.com

