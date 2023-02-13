# API REST

[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/douglasdotv/spring-boot-api-rest/blob/main/README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](https://github.com/douglasdotv/spring-boot-api-rest/blob/main/README.pt-br.md)

Esta é uma API RESTful construída usando Spring Boot 3 para gerenciar algumas informações a respeito de médicos.

Endpoints
(POST) /médicos: permite registrar um novo médico enviando as informações do médico no corpo do json.

(GET) /médicos: retorna uma lista paginada de todos os médicos ativos ordenados por nome.

(GET) /médicos/{id}: retorna as informações de um médico via ID.

(PUT) /médicos: permite atualizar as informações de um médico existente enviando as informações atualizadas do médico no corpo do json.

(DELETE) /médicos/{id}: permite realizar a exclusão lógica de um médico via ID.

(POST) /auth: Este endpoint autentica um usuário pelo seu login e senha. Em caso de autenticação bem-sucedida, um token JSON Web é retornado.

(***Nota***: é necessário enviar um token válido no cabeçalho *Authorization* de cada request. A exceção é o endpoint /auth, que não requere jwt.)

### Exemplos de requests

#### POST: /auth

Request:

```json
{
    "login": "admin@admin.com",
    "password": "123456"
}
```

Response:

```
token
```


#### GET: /doctors

Headers:

```
Authorization: Bearer [AUTH_TOKEN]
```

Response:

```json
{
  "content": [
    {
      "id": 1,
      "name": "John Doe",
      "email": "johndoe@email.com",
      "crm": "111111",
      "specialty": "CARDIOLOGY"
    },
    {
      "id": 2,
      "name": "Jane Doe",
      "email": "janedoe@email.com",
      "crm": "222222",
      "specialty": "ORTHOPEDICS"
    }
  ],
  "pageable": {
    "sort": {
      "empty": false,
      "unsorted": false,
      "sorted": true
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 2,
    "unpaged": false,
    "paged": true
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 2,
  "size": 2,
  "number": 0,
  "sort": {
    "empty": false,
    "unsorted": false,
    "sorted": true
  },
  "numberOfElements": 2,
  "first": true,
  "empty": false
}
```

### ErrorHandler
A aplicação retorna códigos de status HTTP adequados e mensagens de erro quando exceções são lançadas. Esse tratamento ocorre na classe ErrorHandler (@RestControllerAdvice).

A exceção EntityNotFoundException é tratada retornando um código de status HTTP 404 Not Found.

A exceção MethodArgumentNotValidException é tratada retornando um código de status HTTP 400 Bad Request, juntamente com uma lista de erros de validação no corpo da resposta. Este mecanismo de manipulação de erros é implementado na classe ErrorHandler, que atua como um local central para o tratamento de exceções na aplicação.

### Spring Security
A aplicação usa o Spring Security para implementar autenticação e autorização. O mecanismo de autenticação é implementado nas classes SecurityConfiguration e SecurityFilter.

Para obter acesso aos recursos da API, é necessário obter um token JWT fazendo um POST request para o endpoint /auth com um nome de usuário e senha válidos. O token deverá ser incluído no cabeçalho de solicitações subsequentes a endpoints protegidos.

O processo de autenticação é gerenciado pela interface AuthenticationManager do Spring Security, e as senhas são hasheadas com o algoritmo BCrypt.

### JWT
A aplicação usa um serviço de token JWT para implementar autenticação. O token JWT é gerado pela classe JsonWebTokenService, responsável por criar e validar tokens. Além disso, é importante notar que o token é configurado para expirar após 2 horas.

### Nota
Esta é apenas uma implementação simples e pode não ser adequada para uso em produção. Será atualizada à medida que eu aprender mais sobre o Spring Boot. (Ainda faltam testes automatizados, por exemplo.)