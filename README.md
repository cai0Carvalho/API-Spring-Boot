## 📦 Projeto Spring Boot - API REST
### Descrição
Este é um projeto desenvolvido com Spring Boot, com a finalidade de criar APIs RESTful seguindo a arquitetura MVC (Model-View-Controller). O sistema está estruturado em controllers, services, entities e repositories, com foco em escalabilidade, segurança e boas práticas.

Agora, o projeto também conta com segurança via Spring Security, autenticação com JWT e documentação da API utilizando Swagger (Springdoc OpenAPI).

## Estrutura do Projeto
A estrutura do projeto segue o padrão abaixo:

```bash
    src/
    ├── main/
    │   ├── java/com/projeto/
    │   │   ├── config/        # Configurações do Springdoc.
    │   │   ├── controller/    # Camada de controle (HTTP)
    │   │   ├── entity/        # Modelos de dados (JPA)
    │   │   ├── repository/    # Interfaces de acesso ao banco
    │   │   ├── service/       # Lógica de negócio
    │   │   └── Projeto1Application.java  # Classe principal da aplicação
```

### 🧱 Padrão Arquitetural (MVC)

- Controller: Lida com as requisições e respostas HTTP.

- Entity: Representa as tabelas do banco via JPA.

- Service: Contém regras de negócio (evita lógica nos controllers).

- Repository: Camada de persistência com Spring Data JPA.

## 🔐 Segurança
O projeto utiliza Spring Security com as seguintes funcionalidades:

- Autenticação baseada em JWT (JSON Web Tokens)

- Senhas criptografadas com BCrypt

- Filtros para autorização de endpoints

- Rotas públicas e protegidas definidas via configuração

## 🧪 Documentação da API (Swagger)
A documentação é gerada automaticamente com o Springdoc OpenAPI.

° Acesse: http://localhost:8081/swagger-ui/index.html

° Documentação em JSON: http://localhost:8081/v3/api-docs

## 🚀 Tecnologias Utilizadas
- Java 17+

- Spring Boot

- Spring Web

- Spring Data JPA

- Spring Security

- JWT

- H2 (banco em memória)

- Swagger / Springdoc OpenAPI

## Como Executar
1. Clone o repositório:

```sh
    git clone https://github.com/cai0Carvalho/API-Spring-Boot.git
```
2. Acesse o diretório do projeto:

```sh
    cd API-Spring-Boot
```
3. Compile e execute a aplicação:

```sh
    mvn spring-boot:run
```
- A API estará disponível em: http://localhost:8081

## 🔑 Endpoints Padrões
Exemplo de endpoints implementados no controller:

### 🔐 Autenticação (com JWT)

° `POST /auth/registro` - Registra um novo usuário e retorna o token JWT

° `POST /auth/login` - Realiza login e retorna o token JWT

### 👤 Usuário autenticado

° `GET /user/info` - Retorna os dados do usuário autenticado (extraídos do token)

° `PUT /api/usuarios/{id}` - Atualiza um usuário

° `DELETE /api/usuarios/{id}` - Remove um usuário