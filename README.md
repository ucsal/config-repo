# config-repo

Repositório de configuração para Spring Cloud Config.

## Estrutura
- `application.yml` – configurações globais (comuns a todos os serviços)
- `account-service-dev.yml` – configurações específicas para o perfil `dev` do account-service
- `account-service-prod.yml` – configurações específicas para o perfil `prod` do account-service

## Como usar
1. O Config Server deve apontar para este repositório (URL pública):  
   `https://github.com/ucsal/config-repo.git`

2. Para alterar uma configuração:
   - Edite o arquivo correspondente (ex.: `account-service-dev.yml`)
   - Commit e push para o repositório
   - Se o client estiver rodando, dispare `POST /actuator/refresh` no cliente para aplicar sem restart (ver README do client)

## Notas de segurança
- Não coloque segredos em texto claro neste repositório em produção.
- Use criptografia (Spring Cloud Config encrypt/decrypt) ou soluções como HashiCorp Vault / cloud KMS.
