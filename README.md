# Spring Cloud Config — Projeto de Exemplo (UCSAL)

Este repositório contém a documentação unificada para o exercício de Spring Cloud Config usado na disciplina. Ele descreve os três repositórios do trabalho e como rodar/testar a solução localmente.

---

## Sumário
1. Propósito do projeto
2. Repositórios
   - config-repo (arquivos .yml)
   - config-server (Spring Cloud Config Server)
   - account-service (cliente que consome configs)
3. Conceitos (configuração externalizada e centralizada)
4. Por que usar Config Server em um banco digital
5. Como rodar (passo-a-passo)
6. Testes (refresh dinâmico)
7. Proteção de segredos (exemplos)
8. Entrega / links

---

## 1. Propósito do projeto
Demonstrar o uso do Spring Cloud Config para **externalizar** e **centralizar** configurações de múltiplos microserviços (neste caso, um `account-service`) em diferentes perfis (dev, prod). O objetivo é mostrar como os serviços podem obter suas configurações sem embutir segredos no código e como aplicar mudanças dinamicamente.

---

## 2. Repositórios
**Atenção:** Este README agrega documentação dos três repositórios necessários:
- `config-repo` — arquivos YAML com as configurações (application.yml, account-service-dev.yml, account-service-prod.yml).
- `config-server` — aplicação Spring Boot com `@EnableConfigServer`, apontando para o `config-repo`.
- `account-service` — cliente Spring Boot que consome configurações do Config Server e expõe endpoints REST (`/greeting`, `/accounts`).

---

## 3. Conceitos
**Configuração externalizada:** manter configurações fora do código-fonte (arquivos, serviços de segredos, variáveis de ambiente), permitindo alterar comportamento sem recompilar.  
**Configuração centralizada:** unificar as configurações de vários serviços em um único ponto (Config Server), com versionamento (Git) e separação por perfis (dev/prod).

---

## 4. Por que usar Config Server em um banco digital
- Consistência entre ambientes (dev/homolog/prod).  
- Maior segurança (evita espalhar senhas no código).  
- Mudanças rápidas sem redeploy (reduz downtime).  
- Auditoria via commits no Git (quem alterou, quando).  
- Escalabilidade operacional.

---

## 5. Como rodar (passo-a-passo resumido)

### 5.1 Rodar o Config Server
1. Entrar na pasta do projeto:
cd config-server

2. Build e run:
mvn clean package
mvn spring-boot:run

3. Teste:
http://localhost:8888/account-service/dev

### 5.2 Rodar o Account Service (cliente)
1. Entrar na pasta:
cd account-service

2. Rodar com perfil dev:
mvn clean package
mvn spring-boot:run -Dspring-boot.run.profiles=dev

3. Endpoints:
- `GET http://localhost:8081/greeting` → mensagem do Config Server
- `GET http://localhost:8081/accounts` → lista mockada

---

## 6. Teste de refresh dinâmico (sem restart)
1. No Git (config-repo), abra `account-service-dev.yml` e altere `myapp.greeting` (por exemplo: "Olá DEV - Atualizado"). Commit & push.  
2. No cliente (account-service) rodando, dispare:
curl -X POST http://localhost:8081/actuator/refresh
3. Verifique:
curl http://localhost:8081/greeting

A resposta deve mostrar a nova mensagem sem reiniciar o serviço.

> Observação: O `account-service` precisa ter `@RefreshScope` no controller/bean que usa as propriedades e `management.endpoints.web.exposure.include: "*"` habilitado para o actuator (somente em ambiente de estudo).

---

## 7. Proteção de segredos (resumo prático)
### 7.1 Criptografia embutida (exemplo rápido)
No `config-server/src/main/resources/application.yml` adicione (apenas para demonstração local):
```yaml
encrypt:
key: minha_chave_demo_123456
Encriptar:
curl -X POST -d 'minhaSenhaSecreta' http://localhost:8888/encrypt
Descriptografar:
curl -X POST -d '{cipher}...' http://localhost:8888/decrypt
