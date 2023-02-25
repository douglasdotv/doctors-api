# API REST

[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/douglasdotv/api-rest/blob/main/README.md)
[![pt-br](https://img.shields.io/badge/lang-pt--br-green.svg)](https://github.com/douglasdotv/api-rest/blob/main/README.pt-br.md)

Esta é uma API REST feita com Spring Boot para gerenciar médicos, pacientes e consultas.

### Documentação
Utilizou-se Swagger UI para gerar uma interface de documentação interativa para a API. Para acessá-la, basta iniciar a aplicação e navegar até http://localhost:8080/swagger-ui.html em um navegador web.

A partir da interface do Swagger UI, você pode visualizar os endpoints disponíveis, enviar requisições e examinar as respostas. A interface também fornece informações detalhadas sobre os parâmetros de solicitação e resposta da API, bem como quaisquer códigos HTTP que possam ser retornados.

### Autenticação
A API utiliza JSON Web Tokens (JWTs) para autenticação. Para autenticar, o usuário deve enviar uma solicitação POST para o endpoint /auth com um nome de usuário e senha válidos. O Spring Security irá lidar com o processo de autenticação verificando as credenciais fornecidas contra o banco de dados de usuários configurado. Se as credenciais forem válidas, um token JWT será gerado usando a chave secreta e algoritmo configurados. A resposta conterá o token JWT, que deve ser incluído no cabeçalho Authorization de solicitações subsequentes a endpoints seguros.

Ao usar JWTs para autenticação, a API é capaz de implementar autenticação sem estado (stateless), o que significa que o servidor não precisa manter nenhum tipo de informação de sessão entre as solicitações. Isso proporciona vários benefícios, incluindo uma escalabilidade aprimorada e uma redução na sobrecarga do servidor. O Spring Security fornece suporte integrado para JWTs por meio de suas classes JwtAuthenticationToken e JwtAuthorizationFilter, tornando fácil a integração de JWTs.

### Tratamento de erros
A aplicação implementa um tratador de erros para retornar códigos de status HTTP apropriados e mensagens de erro quando exceções são lançadas. O tratador de erros é implementado na classe ErrorHandler e fornece mensagens de erro detalhadas para casos comuns.

### Testes automatizados
Esta aplicação inclui alguns testes unitários para garantir que componentes estejam funcionando como esperado. Os testes estão situados no diretório src/test e podem ser executados usando o comando ./mvnw test.

### Tecnologias utilizadas
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

### Nota
Este projeto foi desenvolvido como parte apenas com o objetivo de praticar meus conhecimentos em Spring Boot. Sendo assim, ele não é destinado para uso em produção ou para ser rodado em seu estado atual.

### Contato
Se houver quaisquer dúvidas ou sugestões, sinta-se à vontade para entrar em contato comigo via [LinkedIn](https://linkedin.com/in/douglasdotv) ou via email (douglas16722@gmail.com).