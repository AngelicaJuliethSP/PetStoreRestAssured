# PetStore Rest Assured Framework

API test automation framework for the [Petstore REST API](https://petstore.swagger.io/) built with Rest Assured, Java, and TestNG, following a layered design (request builder → models → tests).

## Overview

This project automates six core scenarios against the Petstore API:

- **Get pet by ID**: retrieve a pet by its identifier and validate the response schema and data.
- **Get pets by status**: query pets filtered by status (`available`, `pending`, `sold`) and validate the list contents.
- **Create order**: place a store order and verify the API confirmation.
- **Create user**: register a new user and verify the API response.
- **Login user**: authenticate with valid credentials and validate the session response.
- **Logout user**: end the session and validate the logout confirmation.

## Tech Stack

| Tool | Version | Purpose |
|---|---|---|
| Java | 17 | Programming language |
| Maven | 3.x | Build and dependency management |
| Rest Assured | 5.x | REST API testing |
| TestNG | 7.10.2 | Test runner and annotations |
| Jackson | 2.x | JSON serialization/deserialization |

## Project Structure

```
PetStoreRestAssured/
├── src/
│   └── test/java/com/globant/automation/
│       ├── config/
│       │   └── TestRunner.java       # Suite setup, config.properties loading
│       ├── model/                    # POJOs mapped from/to JSON
│       │   ├── ApiResponse.java
│       │   ├── Category.java
│       │   ├── Order.java
│       │   ├── Pet.java
│       │   ├── Tag.java
│       │   └── User.java
│       ├── request/
│       │   └── RequestBuilder.java   # Rest Assured request specification
│       └── test/
│           ├── GetPetByIdTest.java
│           ├── GetPetsByStatusTest.java
│           ├── CreateOrderTest.java
│           ├── CreateUserTest.java
│           ├── LoginUserTest.java
│           └── LogoutUserTest.java
├── src/test/resources/
│   └── config.properties             # Base URL configuration
├── testng.xml
├── pom.xml
└── .gitignore
```

## Design Decisions

- **Layered architecture**: `RequestBuilder` centralizes the Rest Assured specification (base URI, content type, logging); POJOs in `model/` handle JSON mapping; test classes contain only assertions and business-level validations.
- **Externalized base URL**: the target API URL is read from `src/test/resources/config.properties`, keeping environments configurable without code changes.
- **Declarative validation**: responses are deserialized to POJOs and validated with TestNG assertions for readable failure messages.

## How to Run

Clone the repository and run:

```bash
mvn clean test
```

### Prerequisites

- JDK 17 or higher
- Maven 3.6+
- Internet access to `https://petstore.swagger.io/v2`

## Author

Angélica Julieth Silva Pérez — College Trainee, Globant
