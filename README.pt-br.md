# API REST

[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/douglasdotv/spring-boot-api-rest/blob/master/README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](https://github.com/douglasdotv/spring-boot-api-rest/blob/master/README.pt-br.md)

Esse projeto abrange a construção de uma API REST simples para gerenciar uma lista de médicos. Ela conta com os seguintes endpoints:

- POST /doctors: cadastrar um novo médico
- GET /doctors: listar todos os médicos (incluindo paginação)
- PUT /doctors: atualizar os dados de um médico já cadastrado
- DELETE /doctors/{id}: remover um médico através do ID

As seguintes tecnologias foram utilizadas:

- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- Project Lombok
- Bean Validation
