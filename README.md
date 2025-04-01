## Projeto Spring Boot
### Descrição
Este é um projeto desenvolvido com Spring Boot, com a finalidade de criar APIs REST utilizando a arquitetura MVC (Model-View-Controller). O projeto contém controllers, serviços, entidades e repositórios bem estruturados para facilitar a manutenção e escalabilidade.

## Estrutura do Projeto
A estrutura do projeto segue o padrão abaixo:

    ```bash
    src/
    ├── main/
    │   ├── java/com/projeto/
    │   │   ├── controller/   # Contém os controllers da aplicação
    │   │   ├── entity/       # Entidades que representam os modelos de dados
    │   │   ├── repository/   # Interfaces de repositórios para acesso ao banco de dados
    │   │   ├── service/      # Camada de serviços com a lógica de negócios
    │   │   ├── Projeto1Application.java  # Classe principal que inicia o Spring Boot
    ```

### O projeto segue o padrão MVC (Model-View-Controller), onde:

- Controller: Recebe requisições e retorna respostas apropriadas.

- Entity: Representa as tabelas do banco de dados, sendo gerenciada pelo JPA/Hibernate para persistência dos dados.

- Service: Contém regras de negócio e evita lógica na camada de controle.

- Repository: Responsável pelo acesso aos dados com Spring Data JPA.

## Tecnologias Utilizadas
- Java 17+

- Spring Boot

- Spring Web

- Spring Data JPA

- H2

## Como Executar
1. Clone o repositório:

```sh
    git clone 
```
2. Acesse o diretório do projeto:

```sh
    cd https://github.com/cai0Carvalho/API-Spring-Boot.git
```
3. Compile e execute a aplicação:

```sh
    mvn spring-boot:run
```
- A API estará disponível em: http://localhost:9090

## Endpoints Padrões
Exemplo de endpoints implementados no controller:

° `GET /api/usuarios` - Retorna a lista de usuários

° `POST /api/usuarios` - Cria um novo usuário

° `PUT /api/usuarios/{id}` - Atualiza um usuário

° `DELETE /api/usuarios/{id}` - Remove um usuário