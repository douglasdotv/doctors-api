[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/douglasdotv/api-rest/blob/main/README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](https://github.com/douglasdotv/api-rest/blob/main/README.pt-br.md)

This is a REST API built with Spring Boot for managing doctors, patients and appointments.
### Documentation
The API is documented using Swagger UI, which provides an interactive documentation interface for the API. To access the Swagger UI, simply start the application and navigate to http://localhost:8080/swagger-ui.html in your web browser.

From the Swagger UI interface, you can view the available endpoints, submit requests, and view the responses. The interface also provides detailed information about the API's request and response parameters, as well as any response codes that may be returned.

### Authentication
The API uses JSON Web Tokens (JWTs) for authentication. In order to authenticate, the user must submit a POST request to the /auth endpoint with a valid username and password. Spring Security will handle the authentication process by checking the provided credentials against the configured user database. If the credentials are valid, a JWT token will be generated using the configured secret key and algorithm. The response will contain the JWT token, which should be included in the Authorization header of subsequent requests to secured endpoints.

By using JWTs for authentication, the API is able to implement stateless authentication, meaning that the server does not need to keep track of any session information between requests. This provides several benefits, including improved scalability and reduced server overhead. Spring Security provides built-in support for JWTs through its JwtAuthenticationToken and JwtAuthorizationFilter classes, making it easy to integrate JWTs into your Spring Boot application.

### Error Handling
The application implements an error handler to return proper HTTP status codes and error messages in response to exceptions. The error handler is implemented in the ErrorHandler class and provides detailed error messages for common exceptions.

### Automated Testing
This application includes some unit tests to ensure that the components are working as expected. The tests are located in the `src/test` directory and can be run using the `./mvnw test` command.

### Technologies used
* Java 17
* Maven
* Spring Boot 3
* Spring Data JPA
* Spring Security
* MariaDB
* Lombok
* Swagger UI
* JUnit 5
* Mockito

### Note
This project was developed with the main purpose of practicing my knowledge of Spring Boot. As such, it is not intended for production use or to be run in its current state.

### Contact
If you have any questions or suggestions, feel free to contact me at [LinkedIn](https://linkedin.com/in/douglasdotv) or via email (douglas16722@gmail.com).