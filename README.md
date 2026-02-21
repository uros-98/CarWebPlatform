# CarWebPlatform 🚗

**CarWebPlatform** is a web application for managing car information. The goal of this project is to provide a functional, secure, and user-friendly platform for viewing, creating, updating, and deleting car data.

The project is developed using **Java**, **Maven** for dependency management and building, and **MySQL** as the database for storing all information.

---

## 🚀 Features

- View a list of cars
- Add new cars
- Update existing car information
- Delete cars from the system
- REST API for frontend communication
- Integration between backend and frontend

---

## 📦 Technologies Used

| Layer   | Technology                  |
|---------|-----------------------------|
| Backend | Java (Maven project)        |
| Database| MySQL                       |
| Frontend| HTML / JavaScript / CSS     |
| Build   | Apache Maven                |

---

## 🛠️ Requirements

Before running the application, make sure you have installed:

✔️ Java Development Kit (JDK 11 or higher)  
✔️ Maven (3.x)  
✔️ MySQL Server (8.x or compatible)  

---

## ⚙️ Setting up MySQL Database

1. Create a new database, prodajavozila*:

```sql
CREATE DATABASE prodajavozila;
Configure the connection in your application.properties or application.yml file:

spring.datasource.url=jdbc:mysql://localhost:3306/prodajavozila?useSSL=false&serverTimezone=UTC
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD
📌 Replace YOUR_DB_USER and YOUR_DB_PASSWORD with your actual credentials.

📦 Build and Run
1. Clone the repository
git clone https://github.com/uros-98/CarWebPlatform.git
cd CarWebPlatform
2. Build the project using Maven
mvn clean install
3. Run the application
mvn spring-boot:run
➡️ The application should be accessible at http://localhost:8080.

🧪 Testing
If the project contains tests, you can run them with:

mvn test
🧩 Dependencies
Example of adding MySQL connector in pom.xml:

<dependency>
  <groupId>com.mysql</groupId>
  <artifactId>mysql-connector-j</artifactId>
  <scope>runtime</scope>
</dependency>
📌 The MySQL Connector/J allows the Java application to communicate with the MySQL database via JDBC. (github.com)

📚 API Documentation
👉 If the REST API exists, you can add a section here with endpoint descriptions and example responses.

👥 Authors
Uros Stanojevic — Original author

📄 License
This project is free to use and develop further — you can add your preferred license (MIT, Apache 2.0, etc.).

❓ Contributing
If you want to contribute:

Fork the repository

Create a feature branch (git checkout -b feature/your-feature)

Submit a Pull Request

📞 Contact
For questions or suggestions, feel free to open an Issue or send a message!
