# Projeto Final Imobiliária Back

Este é o backend do projeto final de imobiliária, desenvolvido com Spring Boot.

## Pré-requisitos

-  Java 17 ou superior
-  Maven
-  MySQL

## Instalação

1. Clone o repositório:

```bash
git clone https://github.com/jpgomesr/ProjetoFinalImobiliariaBack.git
```

2. Navegue até o diretório do projeto:

```bash
cd ProjetoFinalImobiliariaBack
```

## Execução

Para iniciar o projeto em modo de desenvolvimento:

```bash
mvn spring-boot:run
```

O projeto estará disponível em [http://localhost:8082/api](http://localhost:8082/api)

## Documentação da API

A documentação completa da API está disponível no Swagger UI:
[http://localhost:8082/api/swagger-ui.html](http://localhost:8082/api/swagger-ui.html)

## Tecnologias Utilizadas

-  Spring Boot
-  Spring Security
-  Spring Data JPA
-  MySQL
-  JWT (JSON Web Tokens)
-  Maven

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── hav/
│   │           └── imobiliaria/
│   │               ├── controller/
│   │               ├── service/
│   │               ├── repository/
│   │               ├── model/
│   │               ├── config/
│   │               └── security/
│   └── resources/
└── test/
```

## Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request
