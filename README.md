# ✈️ Flight Booking Application

A backend flight booking system built with **Spring Boot**, **Spring Security**, **Spring Data JPA/Hibernate**, and **MySQL**. It exposes RESTful APIs to manage flights, passengers, bookings, and payments.

## 🚀 Features

- Manage flights, passengers, and bookings through REST APIs
- Booking lifecycle with status management: **confirmed, cancelled, completed**
- Payment workflow with **refunds and partial refunds**
- Secure endpoints using Spring Security
- Request validation with proper error responses
- Centralized exception handling using `@ControllerAdvice`
- AOP-based logging for API operations
- DTO mapping using MapStruct

## 🛠️ Tech Stack

| Layer          | Technology                      |
|----------------|---------------------------------|
| Language       | Java 17 (TODO: your version)    |
| Framework      | Spring Boot                     |
| Security       | Spring Security                 |
| Database       | MySQL                           |
| ORM            | Spring Data JPA / Hibernate     |
| Mapping        | MapStruct                       |
| Cross-cutting  | Spring AOP                      |
| Build Tool     | Maven                           |
| API Testing    | Postman                         |

## 🏗️ Architecture

Layered architecture:

```
Controller → Service → Repository → MySQL
```

- **Controller**: handles REST requests and responses
- **Service**: business logic (booking and payment workflows)
- **Repository**: database access using JPA
- **DTO / Mapper**: MapStruct maps between entities and DTOs
- **Aspect**: AOP logging
- **Exception handler**: global error handling

## 🔄 Booking and Payment Flow

1. Passenger books a flight, so the booking is created
2. Payment is made, so the booking is **CONFIRMED**
3. If cancelled, a **refund** (full or partial) is processed
4. After the journey, the booking is marked **COMPLETED**

(TODO: adjust the statuses to match your enums)

## ⚙️ Getting Started

### Prerequisites
- JDK 17+
- Maven
- MySQL
