# UltraFit — Healthcare & Fitness Management System

UltraFit is a personal training and fitness membership management web application built with **Spring Boot 3.2.5** and **Java 17**. It allows fitness centers and gym members to schedule, reschedule, and manage training sessions with certified trainers, customize subscription plans, and manage their profiles. It features a responsive web interface rendered using Mustache templates, as well as a full REST API for programmatic integrations.

---

##  Key Features

*   **User Authentication & Authorization:** Simple and secure session-based authentication using standard servlet `HttpSession` (no Spring Security configuration required).
*   **Interactive Dashboard:** A dashboard for gym members where they can:
    *   View their profile and subscription plan.
    *   Upgrade or modify their gym membership plan (Basic, Premium, VIP).
    *   Schedule new training sessions, picking a certified trainer, date, time, and session difficulty level.
    *   Modify, cancel, or change the level of active reservations.
    *   Update their personal profile details (Name, Surname, Email, Phone).
*   **Trainer Portfolio:** View gym trainers, their specialties (e.g., Boxing, Yoga, CrossFit), and years of experience.
*   **Full REST APIs:** Independent REST controllers exposing CRUD and PATCH mappings for programmatic management of members, trainers, and reservations.
*   **Auto Data Initialization:** Automatically seeds initial trainers and a test member on application startup.

---

##  Technology Stack

*   **Backend Framework:** Spring Boot 3.2.5
*   **Programming Language:** Java 17
*   **Database:** H2 Database Engine (File-based storage at `./data/ultrafitdb` for persistent records)
*   **ORM / JPA:** Hibernate & Spring Data JPA
*   **Frontend Engine:** Mustache Template Engine (served as server-side rendered HTML)
*   **Boilerplate reduction:** Lombok
*   **Build Tool:** Maven

---

##  Prerequisites

To run this application locally, you will need:
- **Java Development Kit (JDK) 17** or higher.
- **Apache Maven 3.6+** installed and configured on your system path.

---

##  Installation & Quick Start

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/<your-username>/healthcare-management-system.git
    cd healthcare-management-system/ultrafit
    ```

2.  **Build the Project:**
    Compile the sources and package the JAR file using Maven:
    ```bash
    mvn clean package
    ```

3.  **Run the Application:**
    Start the Spring Boot application using Maven:
    ```bash
    mvn spring-boot:run
    ```

4.  **Access the Application:**
    Open your web browser and navigate to:
    *   **Main Application Webpage:** [http://localhost:8082](http://localhost:8082)
    *   **H2 Database Console:** [http://localhost:8082/h2-console](http://localhost:8082/h2-console)

> [!IMPORTANT]
> The server is configured to run on port **8082** (instead of the Spring Boot default 8080) to avoid port collision.

---

##  Default Seed Data & Testing Credentials

The application uses an automatic SQL initializer that populates the database on every startup. You can test the platform immediately using the default credentials:

*   **Test Member Credentials:**
    *   **Username:** `Test` (Email: `test@ultrafit.com`)
    *   **Password:** `1234`
*   **Preloaded Trainers:**
    *   Carlos López (Specialty: Boxing, Experience: 8 years)
    *   Ana Martínez (Specialty: Yoga, Experience: 5 years)
    *   Pedro Sánchez (Specialty: CrossFit, Experience: 10 years)

---

##  Database Console (H2 Console)

You can manage the database tables directly through the browser console. Use the following credentials on the login form:

*   **Console URL:** [http://localhost:8082/h2-console](http://localhost:8082/h2-console)
*   **JDBC URL:** `jdbc:h2:file:./data/ultrafitdb`
*   **Username:** `sa`
*   **Password:** *(leave blank)*

> [!NOTE]
> Database schema creation is set to `create-drop` in `application.properties`, meaning the database tables are recreated on each startup, and seeded using `src/main/resources/data/data.sql`.

---

##  REST API Endpoints

The project exposes a complete set of RESTful JSON endpoints under `/api`. Below are the base paths:

| Entity | Base REST URL | Operations Supported |
| :--- | :--- | :--- |
| **Members** | `/api/members` | Full CRUD (GET all/by ID, POST, PUT, PATCH, DELETE) |
| **Trainers** | `/api/trainers` | Full CRUD (GET all/by ID, POST, PUT, PATCH, DELETE) |
| **Reservations** | `/api/reservations` | Full CRUD (GET all/by ID, POST, PUT, PATCH, DELETE) |

A preconfigured Postman collection is included in the project directory for testing the API:
*   [UltraFit.postman_collection.json](file:///src/postmanFiles/UltraFit.postman_collection.json)

---

##  License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
