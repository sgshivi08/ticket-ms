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

```bash
git clone https://github.com/your-repo/ticket-microservice.git
cd ticket-microservice


API Endpoints
Book a Ticket
URL: /tickets
Method: POST
Request Body:
json
Copy code
{
    "eventId": 1,
    "userName": "John Doe",
    "ticketType": "Standard",
    "numberOfTickets": 2,
    "paymentAmount": 50.00
}
Response:
json
Copy code
{
    "id": 1,
    "eventId": 1,
    "userName": "John Doe",
    "ticketType": "Standard",
    "numberOfTickets": 2,
    "paymentAmount": 50.00
}
Cancel a Ticket
URL: /tickets/{id}
Method: DELETE
Response: 204 No Content

Check Ticket Availability
URL: /tickets/availability
Method: GET
Query Parameters:
eventId (required)
Response:
json
Copy code
{
    "availableTickets": 100
}

Testing
To run unit tests, use:

bash
Copy code
mvn test
Exception Handling
The microservice uses Spring's @ControllerAdvice for centralized exception handling. Common exceptions are:

TicketNotFoundException: If the requested ticket is not found.
InsufficientTicketsException: If there are not enough tickets available for booking.
Configuration
Application Properties: Configure application settings in src/main/resources/application.properties.
