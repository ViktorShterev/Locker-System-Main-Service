# User Service – SmartLockerHub

The **User Service** is the central identity and access management module of the **SmartLockerHub** system. It handles customer and courier registrations, authentication, and email notification workflows, integrating tightly with the other microservices - [**Locker-Service**](https://github.com/ViktorShterev/Locker-System-Locker-Service/tree/master) and [**Package-Service**](https://github.com/ViktorShterev/Locker-System-Package-Service/tree/master) through Kafka and secure REST communication.

---

## 🔧 Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Security (JWT)**
- **Spring Data JPA**
- **Thymeleaf**
- **MySQL**
- **Apache Kafka**
- **Jakarta Validation**
- **JavaMailSender**
- **REST API**

---

## 📦 Features

- User registration with unique email validation
- Secure login and JWT token generation
- Automatically stores and validates access codes for received packages
- Sends email notifications with access codes and delivery information
- Listens to Kafka events (`package-placed`) and persists access code data

---

## ✅ Endpoints

| Method | Endpoint                    | Description                           |
|--------|-----------------------------|---------------------------------------|
| POST   | `/register`                 | Register a new user (courier/customer)|
| POST   | `/login`                    | Authenticate and retrieve JWT token   |
| POST   | `/customer/receive-package` | Validate and mark package as received |
| GET    | `/customer/view-packages`   | List last 10 packages for a customer  |

---

## 📥 Kafka Integration

- **Consumer Topic:** `package-placed`  
  - Receives events with locker unit info and access code
  - Creates and stores the access code in the database
  - Sends an email notification to the recipient
    
- **Consumer Topic:** `package-received`  
  - Receives events that package is picked up
  - Sends an thankful email notification to the customer
