# Shoe Store Backend API

A comprehensive backend service for an e-commerce shoe store built with Spring Boot, featuring modern architecture patterns, JWT authentication, and advanced search capabilities.

## Project Overview

This is a RESTful API backend application designed to support a shoe store e-commerce platform. The application provides complete functionality for managing products, users, inventory, and orders with secure authentication and efficient search capabilities.

## Technology Stack

Core Framework:
- Java 21
- Spring Boot 3.5.6
- Spring Framework 6.x

Database:
- MySQL 8.0 (Primary Database)
- Elasticsearch 7.17.10 (Search and Analytics)

Security:
- Spring Security with OAuth2 Resource Server
- JWT (JSON Web Tokens) for authentication

File Management:
- Cloudinary for cloud-based image storage

API Documentation:
- SpringDoc OpenAPI 2.8.14

Development Tools:
- Maven 3.9
- Lombok 1.18.40
- MapStruct 1.6.3
- Spotless 2.46.1 (Code formatting)

## Project Structure

```
src/
├── main/
│   ├── java/com/nguyenkhang/shoe_store/
│   │   ├── ShoeStoreApplication.java          (Main application entry point)
│   │   ├── annotations/                       (Custom annotations)
│   │   ├── configuration/                     (Spring configuration classes)
│   │   ├── controller/                        (REST API endpoints)
│   │   ├── dto/                               (Data Transfer Objects)
│   │   ├── entity/                            (JPA entities)
│   │   ├── enums/                             (Enumeration definitions)
│   │   ├── exception/                         (Exception handling)
│   │   ├── listener/                          (Event listeners)
│   │   ├── mapper/                            (MapStruct mappers)
│   │   ├── repository/                        (Data access layer)
│   │   ├── service/                           (Business logic)
│   │   ├── specification/                     (JPA specifications for filtering)
│   │   └── utils/                             (Utility classes)
│   └── resources/
│       └── application.yaml                   (Application configuration)
├── test/                                      (Test cases)
```

## Key Features

Authentication & Security:
- JWT-based authentication with configurable token validity
- OAuth2 Resource Server integration
- Refresh token mechanism

Product Management:
- Complete CRUD operations for shoe products
- Advanced filtering and search with Elasticsearch
- Image upload and management via Cloudinary
- Support for product variations and inventory tracking

Search & Filtering:
- Full-text search capabilities using Elasticsearch
- Dynamic filtering specifications
- Optimized query performance

API Documentation:
- Automatic API documentation generation with Swagger UI
- OpenAPI 3.0 specification

Deployment:
- Docker containerization
- Docker Compose orchestration
- Multi-stage Docker build for optimized images

## Prerequisites

- Java 21 or higher
- Maven 3.9 or higher
- Docker and Docker Compose (for containerized deployment)
- MySQL 8.0
- Elasticsearch 7.17.10

## Installation & Setup

Clone the Repository:
```bash
git clone https://github.com/Aether455/shoe_store_BE.git
cd shoe_store_BE
```

Configure Environment Variables:

Copy the example environment file:
```bash
cp .env.example .env
```

Edit `.env` with your configuration:
```
DBMS_CONNECTION=jdbc:mysql://mysqldb:3306/your_database_name
DBMS_USERNAME=root
DBMS_PASSWORD=your_password

ELASTICSEARCH_DBMS_CONNECTION=http://elasticsearch:9200

JWT_SIGNER_KEY=your_secret_key
JWT_VALID_DURATION=3600
REFRESHABLE_DURATION=604800

CLOUDINARY_CLOUD_NAME=your_cloud_name
CLOUDINARY_API_KEY=your_api_key
CLOUDINARY_API_SECRET=your_api_secret
```

Build the Application:
```bash
./mvnw clean package
```

Run with Docker Compose:
```bash
docker-compose up -d
```

Run Locally (without Docker):
```bash
./mvnw spring-boot:run
```

## Configuration

Application Configuration:

The application settings are configured in `src/main/resources/application.yaml`:

```yaml
server:
  port: 8080
  servlet:
    context-path: /api

spring:
  datasource:
    url: ${DBMS_CONNECTION}
    username: ${DBMS_USERNAME}
    password: ${DBMS_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
  elasticsearch:
    uris: ${ELASTICSEARCH_DBMS_CONNECTION}

jwt:
  signerKey: ${JWT_SIGNER_KEY}
  valid-duration: ${JWT_VALID_DURATION}
  refreshable-duration: ${REFRESHABLE_DURATION}

cloudinary:
  cloud_name: ${CLOUDINARY_CLOUD_NAME}
  api_key: ${CLOUDINARY_API_KEY}
  api_secret: ${CLOUDINARY_API_SECRET}
```

Database:
- Automatic schema generation and updates via Hibernate
- MySQL native password authentication

File Upload:
- Maximum file size: 50MB
- Maximum request size: 50MB

## API Endpoints

The API is accessible at: `http://localhost:8080/api`

API documentation is available at: `http://localhost:8080/api/swagger-ui.html`

## Docker Deployment

Build Docker Image:
```bash
docker build -t shoe-store-api .
```

Docker Compose Services:

1. MySQL Database (Port 3306)
    - Handles all data persistence
    - Automatic backup volume management

2. Elasticsearch (Port 9200)
    - Provides search and analytics
    - Memory-optimized configuration (512MB)
    - Data persistence enabled

3. Backend API (Port 8080)
    - Spring Boot application
    - Auto-restart on failure
    - Environment variables loaded from .env

## Build Specifications

Maven Plugins:
- Spring Boot Maven Plugin: Application packaging and execution
- Maven Compiler Plugin: Java 21 compilation with annotation processors
- Spotless Maven Plugin: Code style enforcement and formatting

Code Quality:
- Automatic unused import removal
- Import ordering enforcement
- Code formatting with Palantir Java Format
- Tab-based indentation (4 spaces per tab)

## Environment Variables Reference

| Variable | Description | Example |
|----------|-------------|---------|
| DBMS_CONNECTION | MySQL connection string | jdbc:mysql://localhost:3306/shoe_store |
| DBMS_USERNAME | Database username | root |
| DBMS_PASSWORD | Database password | password123 |
| ELASTICSEARCH_DBMS_CONNECTION | Elasticsearch connection URL | http://localhost:9200 |
| JWT_SIGNER_KEY | Secret key for JWT signing | your_secret_key_here |
| JWT_VALID_DURATION | JWT token validity in seconds | 3600 |
| REFRESHABLE_DURATION | Refresh token validity in seconds | 604800 |
| CLOUDINARY_CLOUD_NAME | Cloudinary cloud name | your_cloud_name |
| CLOUDINARY_API_KEY | Cloudinary API key | your_api_key |
| CLOUDINARY_API_SECRET | Cloudinary API secret | your_api_secret |

## Development Guide

Code Standards:
- Uses Lombok for reducing boilerplate code
- MapStruct for type-safe DTO mapping
- Spotless for consistent code formatting
- Custom annotations for common functionality

Package Organization:
- Controllers handle HTTP requests
- Services implement business logic
- Repositories manage data access
- DTOs transfer data between layers
- Entities represent database schema

Build and Run Tests:
```bash
./mvnw test
```

## Performance Considerations

MySQL Configuration:
- Connection pooling with optimized settings
- Index creation on frequently queried columns
- Query optimization with Specifications

Elasticsearch:
- Indexed data for fast full-text search
- Separate cluster from primary database
- Memory-limited to prevent server crashes

Docker Optimization:
- Multi-stage build reduces image size
- Base image: Eclipse Temurin 21-JRE-Alpine
- Timezone set to Asia/Ho_Chi_Minh (Vietnam)

## Troubleshooting

Common Issues:

1. MySQL Connection Failed
    - Verify MySQL service is running
    - Check DBMS_CONNECTION string matches your setup
    - Ensure credentials are correct in .env

2. Elasticsearch Connection Error
    - Verify Elasticsearch service is running on port 9200
    - Check ELASTICSEARCH_DBMS_CONNECTION URL
    - Ensure ES memory allocation is sufficient

3. Docker Build Fails
    - Clear Maven cache: `./mvnw clean`
    - Rebuild: `./mvnw clean package`
    - Check Docker disk space

4. JWT Token Issues
    - Verify JWT_SIGNER_KEY is set in .env
    - Check token expiration times
    - Ensure token format is correct in Authorization header

## License

This project is provided as-is for development and educational purposes.

## Support

For issues, questions, or contributions, please refer to the GitHub repository issues section.

## Additional Notes

- The application automatically updates database schema on startup (Hibernate ddl-auto: update)
- Cloudinary is required for image upload functionality
- Elasticsearch is required for product search features
- JWT tokens should be included in the Authorization header as: `Bearer <token>`
- The API context path is `/api`, so all endpoints should be prefixed with this path

## Version Information

- Application Version: 0.0.1-SNAPSHOT
- Java Version: 21
- Spring Boot Version: 3.5.6
- Maven: 3.9
```