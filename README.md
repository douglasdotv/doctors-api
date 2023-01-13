# REST API

[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/douglasdotv/spring-boot-api-rest/blob/master/README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](https://github.com/douglasdotv/spring-boot-api-rest/blob/master/README.pt-br.md)

This project is a simple RESTful API for managing a list of doctors. It provides the following endpoints:

- POST /doctors: register a new doctor
- GET /doctors: list all doctors (the list can be paginated by passing size and sort query parameters)
- PUT /doctors: update an existing doctor
- DELETE /doctors/{id}: remove a doctor by ID

The following technologies were used:

- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Project Lombok
- Bean Validation
