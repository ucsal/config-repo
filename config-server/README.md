# Config Server — Spring Cloud Config

Este repositório contém o servidor de configuração utilizado no trabalho da disciplina.

## Como funciona

- Ele aponta para o repositório `config-repo`, que contém os arquivos `.yml` com as configurações.
- Expõe as configurações via HTTP, por exemplo:
http://localhost:8888/account-service/dev

## Como executar
mvn spring-boot:run

O servidor inicia na porta **8888**.

## Relacionamento com os outros repositórios

- **config-repo** → onde ficam as configurações versionadas.
- **account-service** → cliente que consome as configurações do Config Server.

Toda explicação completa está no README consolidado do trabalho final.