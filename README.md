# Customer Management System

A modern, full-stack customer management application consisting of a **Spring Boot REST API backend** and a **JavaFX desktop client**. This system allows users to efficiently manage customer data with a user-friendly graphical interface.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Project Structure](#project-structure)
- [Technologies & Requirements](#technologies--requirements)
- [Backend Setup](#backend-setup)
- [Desktop Application Setup](#desktop-application-setup)
- [API Communication](#api-communication)
- [Features](#features)
- [Getting Started](#getting-started)
- [Running the Project](#running-the-project)

---

## Project Overview

The Customer Management System is a distributed application designed to manage customer data efficiently. The project consists of two main components:

- **Backend (REST API)**: A Spring Boot application serving RESTful endpoints for customer CRUD operations
- **Desktop Client**: A JavaFX desktop application providing an intuitive UI for interacting with the backend

### Key Technologies

| Component | Technology Stack |
|-----------|-----------------|
| **Backend** | Spring Boot 3.5.14, Java 21, MySQL, JPA/Hibernate, Spring Security |
| **Desktop** | JavaFX 21, Java 21, Gson (JSON processing) |
| **Build Tool** | Maven 3.6+ |

---

## Project Structure

```
customer-management/
├── CustomerManagementApplication/     # Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/task/CustomerManagementApplication/
│   │   │   │   ├── config/              # Configuration classes
│   │   │   │   ├── controller/          # REST API endpoints
│   │   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── entities/            # JPA entity classes
│   │   │   │   ├── mappers/             # MapStruct mappers
│   │   │   │   ├── repository/          # Data access layer
│   │   │   │   ├── services/            # Business logic layer
│   │   │   │   ├── specification/       # JPA specifications for queries
│   │   │   │   └── CustomerManagementApplication.java  # Main Spring Boot class
│   │   │   └── resources/
│   │   │       ├── application.properties  # Configuration
│   │   │       └── schema.sql              # Database schema
│   │   └── test/                        # Unit tests
│   └── pom.xml                          # Maven dependencies
│
├── CustomerDesktopApp/                 # JavaFX Desktop Application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/customer/desktop/
│   │   │   │   ├── model/               # Data models
│   │   │   │   ├── service/             # Service layer (API communication)
│   │   │   │   ├── ui/                  # JavaFX UI controllers
│   │   │   │   └── Main.java            # Application entry point
│   │   │   └── resources/               # FXML files, images, etc.
│   │   └── test/                        # Unit tests
│   └── pom.xml                          # Maven dependencies
│
└── README.md                            # This file
```

---

## Technologies & Requirements

### System Requirements

- **Java Development Kit (JDK)**: Java 21 or higher
- **Maven**: Version 3.6.0 or higher
- **MySQL Database**: Version 8.0 or higher
- **Operating System**: Windows, macOS, or Linux


## Backend Setup


### Verify Backend is Running

The backend will start on: **http://localhost:8080**

Check the console output for:
```
Started CustomerManagementApplication in X.XXX seconds
```

---

## Desktop Application Setup

### Building the Desktop Application

1. Navigate to the desktop application directory:
   ```bash
   cd CustomerDesktopApp
   ```

2. Build the project using Maven:
   ```bash
   mvn clean install
   ```

3. Run the desktop application:
   ```bash
   mvn javafx:run
   ```

   Or using direct execution:
   ```bash
   mvn exec:java -Dexec.mainClass="com.customer.desktop.Main"
   ```

### Desktop App Connection Settings

The desktop application connects to the backend through HTTP requests. Ensure the backend is running before launching the desktop app.

**Default Backend URL:** `http://localhost:8080`

If you're running the backend on a different machine or port, you may need to update the API base URL in the desktop application's configuration or service layer files.

---

## API Communication

The desktop application communicates with the Spring Boot backend using **RESTful HTTP requests** over JSON. The following HTTP methods are used:

### HTTP Operations

- **GET** - Retrieve customer data (fetch single or multiple customers)
- **POST** - Create new customer records
- **PUT** - Update existing customer information
- **DELETE** - Remove customer records

### Example API Endpoints

All endpoints are prefixed with the base URL: `http://localhost:8080/api`

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/customers` | Retrieve all customers |
| GET | `/customers/{id}` | Retrieve a specific customer by ID |
| POST | `/customers` | Create a new customer |
| PUT | `/customers/{id}` | Update an existing customer |
| DELETE | `/customers/{id}` | Delete a customer |

---

## Features

The Customer Management System provides the following functionality:

### 1. Display Customers
- View all customers in a table format
- Display customer details including name, email, and phone number
- Search and filter customer records

### 2. Add Customer
- Create new customer records through a user-friendly form
- Validate input fields before submission
- Automatic confirmation upon successful creation

### 3. Update Customer
- Modify existing customer information
- Select customer from the list and edit details
- Real-time validation of changes

### 4. Delete Customer
- Remove customer records from the system
- Confirmation dialog before deletion to prevent accidental removal
- Automatic update of customer list upon deletion

---

## Getting Started

### Prerequisites Checklist

Before running the project, ensure you have:

- [ ] Java 21 JDK installed and `JAVA_HOME` set
- [ ] Maven 3.6+ installed and added to PATH
- [ ] MySQL 8.0+ installed and running
- [ ] Git (for cloning/version control)

### Initial Setup Steps

1. **Clone the Repository** (if applicable)
   ```bash
   git clone <repository-url>
   cd customer-management
   ```

2. **Create Database**
   ```sql
   mysql -u root -p
   CREATE DATABASE customer_db;
   CREATE USER 'root'@'localhost' IDENTIFIED BY 'root';
   GRANT ALL PRIVILEGES ON customer_db.* TO 'root'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **Verify Java and Maven**
   ```bash
   java -version    # Should show Java 21+
   mvn -version     # Should show Maven 3.6+
   ```

---

## Running the Project

### Step-by-Step: Complete Project Execution

### Step 1: Start MySQL via Docker
```bash
cd CustomerManagementApplication
docker-compose up -d
```

Verify MySQL is running:
```bash
docker ps
```

You should see `customer_mysql` container with status `Up`.

---

#### Step 2: Build and Run the Backend

Open a terminal and execute:

```bash
cd CustomerManagementApplication
mvn clean install
mvn spring-boot:run
```

Wait for the message: **"Started CustomerManagementApplication in X.XXX seconds"**

#### Step 3: Build and Run the Desktop Application

Open another terminal window and execute:

```bash
cd CustomerDesktopApp
mvn clean install
mvn javafx:run
```

The JavaFX desktop application window will open automatically.

#### Step 4: Verify the Connection

- The desktop app should now connect to the backend
- You should be able to see an empty customer list initally
- Try adding a new customer to verify end-to-end functionality

### Complete Command Sequence

For quick reference, here's the complete sequence to run the full project:

```bash
# Terminal 1: Start Backend
cd customer-management/CustomerManagementApplication
mvn spring-boot:run

# Terminal 2: Start Desktop App (in a new terminal)
cd customer-management/CustomerDesktopApp
mvn javafx:run
```

---

## Troubleshooting

### Backend Won't Start
- Verify MySQL is running: `mysql -u root -p`
- Check database credentials in `application.properties`
- Ensure port 8080 is not in use

### Desktop App Can't Connect to Backend
- Verify backend is running at `http://localhost:8080`
- Check network connectivity
- Review application logs for API errors

### Build Failures
- Run `mvn clean install -X` for detailed debug output
- Ensure Java 21 is being used: `java -version`
- Clear Maven cache: `mvn clean`

---

## Further Development

To extend this project, consider:

- Adding user authentication/authorization features
- Implementing pagination for large datasets
- Adding data export functionality (CSV, PDF)
- Implementing advanced search and filtering
- Adding transaction history tracking
- Deploying backend to a cloud platform (AWS, Azure, Heroku)

---

## License

This project is provided as-is for educational and development purposes.
