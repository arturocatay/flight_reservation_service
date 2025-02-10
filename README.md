# Flight Reservation Service

## OpenAPI & Swagger Integration

The service can be accessed and instantiated through OpenAPI Swagger.

Swagger UI: http://localhost:8080/swagger-ui.html Ensure your API documentation is exposed via Swagger UI for easy access and testing.

## Local Test
- Run the docker-compose.yml file in order to deploy all containers needed (zookepper,kafka,postgres)
- Run the sql script located in \resources\script_db\dummy_insert.sql if you want to execute locally after run the service.

## Overview
The Flight Reservation Service is a microservice that allows users to reserve and manage flight seats. It uses PostgreSQL as the database, Kafka for messaging, and is containerized using Docker.

## Features
- Reserve a seat on a flight
- Cancel a reservation
- Retrieve reservation details
- Retrieve reservations for a specific flight
- Get available seats for a flight

## API Endpoints

### Reserve a Seat
**Endpoint:** `POST /reservations/reserve`

**Parameters:**
- `flightNumber` (String) - Flight number
- `seatNumber` (String) - Seat number
- `userEmail` (String) - User email

**Response:**
- `200 OK` - Seat reserved successfully
- `400 BAD REQUEST` - Flight not found or seat unavailable

### Cancel a Reservation
**Endpoint:** `POST /reservations/cancel`

**Parameters:**
- `flightNumber` (String) - Flight number
- `seatNumber` (String) - Seat number
- `reservationNumber` (String) - Reservation number

**Response:**
- `200 OK` - Reservation canceled successfully
- `400 BAD REQUEST` - Errors like flight not found, seat not found, or already canceled

### Get Reservation by Number
**Endpoint:** `GET /reservations/reservation/{reservationNumber}`

**Response:**
- `200 OK` - Returns reservation details

### Get Reservations for a Flight
**Endpoint:** `GET /reservations/flight/{flightNumber}`

**Response:**
- `200 OK` - List of reservations for the given flight

### Get Available Seats for a Flight
**Endpoint:** `GET /reservations/flight/{flightNumber}/available-seats`

**Response:**
- `200 OK` - List of available seats
- `400 BAD REQUEST` - Flight not found or no seats available

## NOTE
After reservation process the service can send an email, by default is disable, change the mail configuration section of application.properties 


## Setup and Deployment

### Prerequisites
- Docker
- Docker Compose
- Java 17+
- Maven 3.9.6

### Running the Service
1. Clone the repository:
   ```bash
   git clone <repository_url>
   cd flight-reservation-service
   ```

2. Start the service using Docker Compose:
   ```bash
   docker-compose up -d
   ```

### Docker Compose Configuration

```yaml
version: '3'
services:
  zookeeper:
    container_name: zookeeper_flight
    image: confluentinc/cp-zookeeper:7.4.0
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  kafka:
    container_name: kafka_flight
    image: confluentinc/cp-kafka:7.4.0
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1

  postgres:
    image: postgres:15
    container_name: db-flight
    restart: always
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: flightdb
    ports:
      - "5432:5432"
```



