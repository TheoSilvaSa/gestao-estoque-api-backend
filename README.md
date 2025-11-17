# Sistema de Gestão de Estoque e Caixa - API Backend

Este repositório contém o código-fonte da API backend para o Sistema de Gestão de Estoque e Caixa. A aplicação é construída em Java com Spring Boot e é responsável por toda a lógica de negócio, segurança e persistência de dados.

## Objetivo do Projeto

Desenvolver um sistema web para controle de estoque, registro de vendas (caixa) e gestão de usuários, com base em uma arquitetura cliente-servidor moderna. [cite_start]A API implementa serviços REST, validações, rotas protegidas por JWT e papéis de usuário (roles).

## Tecnologias Utilizadas

O backend foi construído com as seguintes tecnologias:

* **Java 17**
* **Spring Boot 3.5.7**
* **Spring Data JPA:** Para persistência de dados.
* **Spring Security:** Para autenticação e autorização.
* **H2 Database (Modo Arquivo):** Banco de dados SQL para persistência.
* **JWT (JSON Web Token):** Para gerenciamento de sessão stateless (via `jjwt`).
* **Spring Validation:** Para validação de DTOs de entrada.
* **Maven:** Para gerenciamento de dependências.

## Como Executar o Projeto

1.  **Pré-requisitos:**
    * JDK 17 ou superior.
    * Maven.

2.  **Clonar o Repositório:**
    ```bash
    git clone [https://github.com/TheoSilvaSa/gestao-estoque-api-backend.git](https://github.com/TheoSilvaSa/gestao-estoque-api-backend.git)
    cd gestao-estoque-api-backend
    ```

3.  **Executar a Aplicação:**
    * **Via Maven:**
        ```bash
        mvn spring-boot:run
        ```
    * **Via IntelliJ IDEA:**
        * Abra o projeto.
        * Encontre a classe `GestaoEstoqueApiApplication.java` e clique no botão "Play" ao lado dela.

4.  **Acessos:**
    * A API estará disponível em: `http://localhost:8080`
    * O console do banco H2 estará disponível em: `http://localhost:8080/h2-console`
        * **JDBC URL (Importante):** `jdbc:h2:file:./gestao-estoque-db`
        * **Usuário:** `sa`
        * **Senha:** (deixe em branco)

## Credenciais de Teste

O sistema inicia com dois usuários padrão, criados pelo `DataInitializer.java`.

| Perfil | E-mail | Senha |
| :--- | :--- | :--- |
| **Administrador** | `admin@gestao.com` | `Admin@123` |
| **Operador** | `operador@gestao.com` | `Operador@123` |

## Documentação da API (Endpoints)

A API é protegida por JWT. Todas as requisições (exceto `/api/auth/login`) devem conter o header `Authorization: Bearer <seu_token>`.

### 1. Autenticação

| Método | Endpoint | Perfil | Descrição |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/login` | **Público** | Autentica o usuário e retorna um token JWT com nome e perfil. |

### 2. Usuários (ADMIN)

| Método | Endpoint | Perfil | Descrição |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/usuarios` | **ADMIN** | Lista todos os usuários cadastrados. |
| `GET` | `/api/usuarios/{id}` | **ADMIN** | Busca um usuário específico pelo ID. |
| `POST` | `/api/usuarios` | **ADMIN** | Cadastra um novo usuário. |
| `PUT` | `/api/usuarios/{id}` | **ADMIN** | Atualiza os dados de um usuário. |
| `DELETE` | `/api/usuarios/{id}` | **ADMIN** | Exclui um usuário. |

### 3. Produtos (ADMIN / OPERADOR)

| Método | Endpoint | Perfil | Descrição |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/produtos` | **ADMIN**, **OPERADOR** | Lista todos os produtos (Necessário para o Operador ver o que vender). |
| `GET` | `/api/produtos/{id}` | **ADMIN**, **OPERADOR** | Busca um produto específico pelo ID. |
| `POST` | `/api/produtos` | **ADMIN** | Cadastra um novo produto. |
| `PUT` | `/api/produtos/{id}` | **ADMIN** | Atualiza um produto. |
| `DELETE` | `/api/produtos/{id}` | **ADMIN** | Exclui um produto. |

### 4. Estoque (ADMIN)

| Método | Endpoint | Perfil | Descrição |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/estoque/movimentar` | **ADMIN** | Registra uma movimentação manual de estoque (Entrada ou Ajuste). |
| `GET` | `/api/estoque/historico` | **ADMIN** | Lista todo o histórico de movimentações de estoque. |

### 5. Vendas / Caixa (OPERADOR)

| Método | Endpoint | Perfil | Descrição |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/vendas` | **OPERADOR** | Registra uma nova venda, valida o estoque, calcula o troco e dá baixa automática nos produtos. |

### 6. Relatórios (ADMIN / OPERADOR)

| Método | Endpoint | Perfil | Descrição |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/relatorios` | **ADMIN**, **OPERADOR** | Gera um relatório de vendas filtrado (por data, valor ou usuário) e retorna os somatórios. |
