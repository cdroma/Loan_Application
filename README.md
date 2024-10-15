# Loan_Application
Rest API based Loan application. The application consists of a backend built with Spring Boot and a frontend developed using Vaadin.
Link to frontend repository : https://github.com/cdroma/Loan_Application_Vaadin
## Requirements
- Java 17 or newer
- Spring Boot 3.x
- MySQL database
- Gradle
## Running the application
### Clone repository
git clone https://github.com/cdroma/Loan_Application
### Build the project with Gradle
./gradlew build
### Configure the database
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

Example for MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/database_name
spring.datasource.username=username
spring.datasource.password=password
### Application backend adres
The application will be available at http://localhost:8080
