🟦 SPRING CLOUD CONFIG — Projeto da Atividade

Este repositório contém a implementação completa de um ambiente utilizando Spring Cloud Config, com Config Server, cliente account-service, configuração externalizada, perfis dev/prod, e demonstração de proteção de dados sensíveis.

O projeto segue exatamente todos os requisitos descritos no enunciado da atividade.

📌 1. Propósito e Funcionalidade do Projeto

O objetivo deste projeto é demonstrar, na prática, como utilizar o Spring Cloud Config para centralizar e externalizar configurações de aplicações distribuídas.
Através desse mecanismo, múltiplos serviços conseguem:

Ler configurações de um único lugar

Atualizar valores sem reiniciar a aplicação

Alternar entre ambientes (dev/prod)

Proteger dados sensíveis (senhas, tokens, chaves)

Nesse projeto, o serviço account-service consome suas configurações a partir do Config Server, que por sua vez lê seus arquivos de configuração armazenados em um repositório Git.

🏦 2. Por que isso é necessário em um banco digital?

No contexto de um banco digital, as aplicações são distribuídas e executam em múltiplos ambientes:

Desenvolvimento (dev)

Homologação (hml)

Produção (prod)

Ter configurações espalhadas dentro de vários arquivos locais implica em:

❌ risco de inconsistência
❌ dificuldade para dar manutenção
❌ risco de senhas expostas
❌ problemas ao escalar múltiplas instâncias

Portanto, o Spring Cloud Config resolve isso permitindo:

✔ Configurações externalizadas
✔ Todas centralizadas em um único local seguro
✔ Controle de versionamento via Git
✔ Alterações aplicadas dinamicamente (sem restart)
✔ Segurança e auditoria para dados sensíveis

Isso deixa o ambiente mais seguro, organizado e confiável, algo essencial para um sistema financeiro.

🧠 3. Conceito de Configuração Externalizada

Configuração externalizada significa que as configurações não ficam dentro da aplicação, como no application.yml.
Em vez disso, elas ficam fora, em:

Repositórios Git

Variáveis de ambiente

Vaults

Servidores de configuração

Benefícios:

Permite mudar configs sem recompilar a aplicação

Evita expor senhas dentro do projeto

Permite usar versões diferentes das configs para cada ambiente

No nosso caso, as configs estão no Git.

🏛️ 4. Conceito de Configuração Centralizada

Configuração centralizada significa que todos os serviços consultam um único servidor para pegar suas configurações.

Esse servidor é o Config Server.

Vantagens:

✔ Manutenção mais simples
✔ Garantia de consistência entre serviços
✔ Ações de auditoria
✔ Facilidade para escalar microserviços
✔ Simplificação na troca de ambiente dev → prod

⚙️ 5. O que foi implementado
✔ Config Server

Configurado em config-server/

Apontando para este repositório Git

Disponibiliza configs via endpoint:

http://localhost:8888/account-service/dev
http://localhost:8888/account-service/prod

✔ Cliente (account-service)

Serviço Java Spring Boot que consome o Config Server

Utiliza bootstrap.yml para buscar configurações externas

Inclui endpoint de teste:

GET /greeting


que lê o valor do Git de acordo com o perfil

Implementação de CRUD simples:

GET /accounts
GET /accounts/{id}
POST /accounts

✔ Perfis DEV e PROD separados

Arquivos no repositório:

account-service-dev.yml
account-service-prod.yml


Cada perfil possui configurações distintas como:

Porta

Mensagens personalizadas

Configuração de banco (fictícia para fins acadêmicos)

✔ Refresh dinâmico

O cliente possui:

@RefreshScope


Permitindo atualizar configurações sem reiniciar a aplicação usando:

POST http://localhost:8081/actuator/refresh

🔐 6. Como proteger configurações sensíveis

Esta parte é teórica e explicativa, conforme solicitado na atividade.

Métodos recomendados:
1. Não colocar senhas em repositórios Git públicos (principal regra)

Sempre use variáveis de ambiente ou Vault.

2. Spring Cloud Config + criptografia

O Config Server oferece:

Criptografia assimétrica (chave pública/privada)

Criptografia simétrica (chave secreta)

Pode-se usar:

encrypt.key = minhaChaveSecreta


E criptografar valores como:

password: '{cipher}AF1223ABCF9822XXX...'

3. HashiCorp Vault

Ferramenta profissional usada em bancos.

Integra automaticamente com Spring Cloud Config.

4. Variáveis de ambiente

Ideal para Docker e Kubernetes:

DB_PASSWORD=${DB_PASSWORD}

🗂️ 7. Estrutura do Repositório
config-repo/
│
├── account-service/
│   ├── src/
│   ├── pom.xml
│   ├── README.md
│
├── config-server/
│   ├── src/
│   ├── pom.xml
│   ├── README.md
│
├── account-service-dev.yml
├── account-service-prod.yml
└── README.md   ← este arquivo

🚀 8. Como rodar o projeto
1. Iniciar o Config Server
cd config-server
mvn spring-boot:run

2. Iniciar o account-service com perfil DEV
cd account-service
mvn spring-boot:run -Dspring-boot.run.profiles=dev

Testar:
http://localhost:8081/greeting

📄 9. Conclusão

Este projeto demonstra a implementação completa de configuração externalizada, centralizada, versionada, com múltiplos perfis e suporte a refresh dinâmico — tudo essencial para sistemas modernos e principalmente para ambientes críticos como bancos digitais.
