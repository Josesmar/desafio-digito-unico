# Desafio Dígito Único

## Sobre o projeto

Uma API que recebe um número inteiro e calcula um dígito único para esse número. A função que calcula o dígito único recebe dois parâmetros:

**n**: número inteiro

**k**: número inteiro que representa o número de vezes que *n* será repetido

*Exemplo:*

```
n = 123, k = 2
digitoUnico(n, k) = 123123
somarDigitos(123123) = 1 + 2 + 3 + 1 + 2 + 3 = 12
```

Se o dígito encontrado ainda não for único, a operação de soma se repete

```
somarDigitos(12) = 1 + 2 = 3
```

O projeto conta também com endpoints para CRUD de Usuário e Dígitos Unicos.

### Tecnologias utilizadas

- Java 8
- Maven
- SpringBoot
- H2 Database

### Ferramentas

- Git (Controle de versão)
- Postman (Teste de endpoints)
- Swagger (Geração de documentação)
- Docker (virtualização de ambiente)
- Lombok (Produtividade e redução de código)
- SonarLint (Qualidade de código)

### Teste

Para testar a aplicação, executar a aplicação de testes disponível em

```
src > test > java > DesafioApplicationTests
```

### Instruções para a execução

o projeto conta com uma classe "DesafioApplication dentro do módulo de API para startar a aplicação através do SpringInitializer.

```
desafio-digito-unico-api > com.desafio.digitounico > DesafioApplication > Run as SpringBoot
```
ou por linha de comando

```bash
java -jar desafio-digito-unico.jar
```

### Paths e endpoints

Path original da API:  [Desafio Único API](http://localhost:8080/api)

Endpoints: [Swagger UI](http://localhost:8080/api/swagger-ui.html)

### Contribuição

Pull requests e comentários são muito bem-vindos! Abra um issue caso a alteração seja significativa para explicar o que deseja alterar. Caso queira me contatar, meu email está cadastrado no perfil.