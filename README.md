# HR Management System API

The HR Management System is a comprehensive software solution designed to streamline human resources processes within organizations. It offers a range of functionalities to manage employee data, jobs, attendance and department management.

## Features
- Employee Management: Add, view, update, and delete employee information.
- Attendance Management: Record and manage employee attendance.
- Department Management: Manage employee departments, including CRUD operations.
- Job Management: Manage employee job, including CRUD operations

## Technologies Used
- Java
- Jakarta EE (formerly Java EE)
- JAX-RS for RESTful services
- JAX-WS for SOAP services
- Maven for project management
- Git for version control

## Getting Started
To get started with the HR Management System, follow these steps:

1. Ensure you have Apache Tomcat installed. If not, you can download it from [here](https://tomcat.apache.org/) and follow the installation instructions provided.
2. Clone this repository
3. Build the project using Maven ``` mvn clean install ```
4. Deploy the generated WAR file to your Apache Tomcat server.
5. Access the RESTful services using their respective endpoints ``` http://localhost:9090/hr/api/{endpoint} ```
6. Access the SOAP services using their respective WSDL URLs ``` http://localhost:9090/hr/soap/{endpoint} ```

## Documentations
- [Rest](https://documenter.getpostman.com/view/23041179/2sA3BgAaVV)

- [Soap](https://documenter.getpostman.com/view/23041179/2sA3BgBvso)
