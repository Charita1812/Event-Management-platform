# Event Management Platform

A secure, role-based Event Management Platform developed using Java and Spring Boot.

The application allows attendees to browse and register for events, organisers to create and manage events, and administrators to manage the entire platform.

## Features

### Authentication

- User registration and login
- JWT-based authentication
- Role-based authorization
- Secure password encryption
- Email verification
- Password reset using expiring tokens
- Protected REST API endpoints

### Attendee Features

- Browse available events
- View event details
- Register for an event
- Cancel an existing registration
- View registered events

### Organiser Features

- Create new events
- Update event information
- Delete owned events
- View created events
- View attendees registered for an event

### Administrator Features

- View and manage platform users
- Assign or update user roles
- View and manage all events
- Monitor platform activity

## User Roles

| Role | Permissions |
|------|-------------|
| `ATTENDEE` | Browse events, register, cancel registration and view registered events |
| `ORGANISER` | Create, update and delete events and view event attendees |
| `ADMIN` | Manage users, roles, events and overall platform activity |

## Technology Stack

- Java 8
- Spring Boot 2.7.18
- Spring Security
- JSON Web Tokens
- Spring Data MongoDB
- MongoDB Atlas
- Redis
- JavaMailSender
- Maven
- Docker
- REST APIs
- JUnit 5
- IntelliJ IDEA

## Architecture

The application follows a layered architecture:

```text
Client
  ↓
Controller
  ↓
Service
  ↓
Repository
  ↓
MongoDB
```

### Application Layers

- **Controller:** Receives and validates HTTP requests
- **Service:** Contains business rules and application logic
- **Repository:** Communicates with MongoDB
- **Security:** Handles JWT authentication and role-based authorization
- **DTO:** Transfers data between the client and application
- **Exception:** Provides centralized error handling

## Project Structure

```text
src/main/java/com/charita/event_platform/
├── config/
├── controller/
├── dto/
├── exception/
├── model/
├── repository/
├── security/
├── service/
└── EventPlatformApplication.java
```

## Core Data Models

### User

```text
id
name
email
password
role
emailVerified
createdAt
```

### Event

```text
id
title
description
location
startDate
endDate
capacity
organiserId
status
createdAt
updatedAt
```

### Registration

```text
id
userId
eventId
registrationStatus
registeredAt
```

## Event Lifecycle

```text
DRAFT → PUBLISHED → ONGOING → COMPLETED
                    ↘ CANCELLED
```

## API Endpoints

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Authenticate a user |
| GET | `/api/auth/verify-email` | Verify an email address |
| POST | `/api/auth/forgot-password` | Request a password-reset link |
| POST | `/api/auth/reset-password` | Reset the user's password |

### Events

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/events` | Get all available events |
| GET | `/api/events/{id}` | Get an event by ID |
| POST | `/api/events` | Create an event |
| PUT | `/api/events/{id}` | Update an event |
| DELETE | `/api/events/{id}` | Delete an event |
| GET | `/api/events/my-created-events` | Get events created by the organiser |
| GET | `/api/events/my-registrations` | Get events registered by the attendee |

### Registrations

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/events/{eventId}/register` | Register for an event |
| DELETE | `/api/events/{eventId}/register` | Cancel an event registration |
| GET | `/api/events/{eventId}/attendees` | View registered attendees |

### Administration

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/users` | Get all users |
| PUT | `/api/admin/users/{id}/role` | Update a user's role |
| GET | `/api/admin/events` | Get all platform events |
| DELETE | `/api/admin/events/{id}` | Remove an event |

> Some endpoint names may differ slightly depending on the final controller mappings.

## Getting Started

### Prerequisites

Install the following software:

- Java 8
- Maven
- MongoDB or MongoDB Atlas
- Redis
- IntelliJ IDEA or another Java IDE
- Docker, if running the application in a container

### Clone the Repository

```bash
git clone https://github.com/Charita1812/event-platform.git
cd event-platform
```

### Configure the Application

Add the required configuration to:

```text
src/main/resources/application.properties
```

Example configuration:

```properties
server.port=8080

spring.data.mongodb.uri=${MONGODB_URI}
spring.data.mongodb.database=event_platform

jwt.secret=${JWT_SECRET}
jwt.expiration=86400000

spring.redis.host=localhost
spring.redis.port=6379

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

Do not commit database credentials, JWT secrets, or email passwords to GitHub. Provide them through environment variables.

### Required Environment Variables

```env
MONGODB_URI=your_mongodb_connection_string
JWT_SECRET=your_secure_jwt_secret
MAIL_USERNAME=your_email_address
MAIL_PASSWORD=your_email_app_password
```

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

The application will start at:

```text
http://localhost:8080
```

## Running with Docker

Build the Docker image:

```bash
docker build -t event-platform .
```

Run the container:

```bash
docker run -p 8080:8080 \
  --name event-platform-container \
  event-platform
```

## Testing the APIs

The APIs can be tested using:

- Postman
- Swagger UI, if configured
- cURL


## Security

- Passwords are stored using secure hashing.
- JWT tokens protect authenticated endpoints.
- Role-based authorization restricts sensitive operations.
- Redis stores temporary password-reset tokens.
- Email-verification links expire after a configured duration.
- Sensitive configuration is loaded through environment variables.

## Future Improvements

- React-based responsive frontend
- QR-code event tickets
- Automatic waitlist promotion
- Scheduled event reminders
- Online payment integration
- Event analytics dashboard


## License

This project is intended for educational and portfolio purposes.
