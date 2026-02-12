# Dating Application - Spring Boot

## Overview

This is a Spring Boot based Dating Application backend. It provides RESTful APIs for user management and dating features.

## Tech Stack

- **Java**: 21
- **Framework**: Spring Boot 3.5.3
- **Build Tool**: Maven
- **Database**: MySQL
- **Documentation**: SpringDoc OpenAPI (Swagger UI)
- **Utilities**: Lombok, Spring Boot DevTools

## Features

- REST API implementation
- Data persistence using Spring Data JPA
- Email support (Spring Boot Starter Mail)
- OpenAPI/Swagger Documentation
- MySQL Database integration

## Prerequisites

- JDK 21
- Maven
- MySQL Server

## Configuration

The application is configured to run on port `1234`.
Database configuration is located in `src/main/resources/application.properties`.

Default settings:

- **Port**: `1234`
- **Database URL**: `jdbc:mysql://localhost:3306/dating`
- **Username**: `root`
- **Password**: `Ashish@99` (Note: It is recommended to change this for your local setup)

## Setup and Running

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   ```

2. **Configure Database**
   Ensure MySQL is running and update `src/main/resources/application.properties` with your database credentials if different from default.

3. **Build the project**

   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```
   Or run the main class `DatingApplication.java` from your IDE.

## API Documentation

Once the application is running, you can access the Swagger UI documentation at:
http://localhost:1234/swagger-ui/index.html

## Project Structure

- `com.mydating.dating.Controller`: REST Controllers
- `com.mydating.dating.entity`: JPA Entities
- `com.mydating.dating.repository`: Data Access Layer
- `com.mydating.dating.service`: Business Logic
- `com.mydating.dating.dto`: Data Transfer Objects
- `com.mydating.dating.util`: Utility classes
