# Ticket Microservice
API Documentation
Swagger documentation is available at http://localhost:8081/swagger-ui.html.

## Overview

The Ticket Microservice is part of a larger event management system responsible for handling ticket booking and management. This microservice allows users to book tickets, cancel them, and check the availability of tickets for specific events.

## Technologies

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **H2 Database (In-Memory)**
- **SpringDoc OpenAPI (Swagger)**
- **JUnit 5 (JUnit Jupiter)**
- **Mockito**

## Setup

### Prerequisites

- Java 17
- Maven (for building the project)

### Clone the Repository

git clone https://github.com/sgshivi08/ticket-ms.git
cd ticket-ms

Build the Project mvn clean install

Run the Application mvn spring-boot:run

The application will start on port 8080 by default.
Testing
To run unit tests, use:

mvn test
Exception Handling
The microservice uses Spring's @ControllerAdvice for centralized exception handling. Common exceptions are:

TicketNotFoundException: If the requested ticket is not found.
InsufficientTicketsException: If there are not enough tickets available for booking.
Configuration
Application Properties: Configure application settings in src/main/resources/application.properties.
