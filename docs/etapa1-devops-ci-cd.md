# Etapa 1 – Configuração do Ambiente DevOps com GitHub Actions e Planejamento

## 1. Objetivo da Etapa

Descrever a configuração do ambiente de integração contínua (CI/CD) utilizando GitHub Actions, incluindo:
- Pipelines de build e testes
- Validação de Conventional Commits
- Geração automática de versão semântica

## 2. Configuração do GitHub Actions

### 2.1. Workflows Criados

- `ci-commitlint.yml`
  - Executa:
    - commitlint (validação de Conventional Commits)
    - `mvn test` (testes automatizados)
  - Dispara em:
    - `push` e `pull_request` para `main` e `dev`

- `release-semver.yml`
  - Executa:
    - análise de commits
    - geração da versão semântica (major/minor/patch)
    - criação de tag e Release no GitHub
    - build do `.jar` com Maven
  - Dispara em:
    - `push` na branch `main`
    - `workflow_dispatch` (execução manual)

### 2.2. Integração com o Projeto

- Projeto principal: `Controle-Ponto/`
- Ferramentas:
  - GitHub Actions
  - Maven
  - Java 21

## 3. Conventional Commits e Commitlint

- Padrão adotado:
  - `feat: descrição`
  - `fix: descrição`
  - `ci: descrição`
  - `docs: descrição`
  - `test: descrição`
- Ferramenta:
  - `@commitlint/cli` + `@commitlint/config-conventional`
- Regra:
  - Todo commit precisa seguir o padrão para o CI passar.

## 4. Versionamento Semântico

- Baseado nos tipos de commit:
  - `feat` → incrementa **minor**
  - `fix` → incrementa **patch**
  - `BREAKING CHANGE` → incrementa **major**
- Automação:
  - Geração de tag (`vX.Y.Z`)
  - Atualização de `CHANGELOG.md`
  - Criação de Release com `.jar` anexado

## 5. Conclusão da Etapa 1

Nesta etapa, o projeto passou a contar com:
- Fluxo automatizado de CI/CD
- Padronização de commits
- Versionamento semântico integrado ao pipeline
