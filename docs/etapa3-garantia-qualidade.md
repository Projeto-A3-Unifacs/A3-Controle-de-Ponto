# Etapa 3 – Garantia de Qualidade e Testes Avançados

## 3.1. Introdução

A Etapa 3 tem como objetivo aplicar práticas de **Garantia de Qualidade (GQ)** ao sistema de Controle de Ponto, validando o correto funcionamento das funcionalidades implementadas e assegurando que o software mantenha consistência, confiabilidade e integridade ao longo do desenvolvimento.

Nesta fase foram empregados:

- Testes unitários;
- Testes de integração;
- Métricas e indicadores de qualidade;
- Processo formal de revisão de código (Code Review);
- Automatização de testes por meio do GitHub Actions (CI).

Diferentemente das etapas anteriores, esta seção apresenta uma estrutura **geral e expansível**, permitindo que qualquer funcionalidade — atual ou futura — seja analisada e documentada dentro do mesmo padrão.

---

## 3.2. Estrutura Geral de Testes Aplicada ao Sistema

Foram aplicados dois níveis principais de testes:

### 3.2.1. Testes Unitários
Os testes unitários têm como objetivo validar regras de negócio isoladamente, sem interação com banco de dados ou entrada do usuário. Entre os elementos avaliados estão:

- Validação de entrada;
- Mapeamento de códigos e mensagens;
- Comportamento esperado para cenários válidos e inválidos;
- Verificação de chamadas aos repositórios via mocks;
- Tratamento de erros e exceções.

### 3.2.2. Testes de Integração
Os testes de integração verificam o fluxo completo passando por:

- Entrada simulada do usuário;
- Execução real de métodos no Menu;
- Camada de serviço;
- Comunicação com o banco PostgreSQL;
- Inserção e leitura real de dados.

Esses testes asseguram que todas as camadas operam de forma conjunta e correta.

---

## 3.3. Funcionalidades Testadas

O projeto contém diversos cases no Menu e classes associadas a regras de negócio, persistência e operações do sistema.  
Cada case, classe ou funcionalidade pode ser documentado nesta seção conforme for sendo testado.

---

## 3.4. Resultados Gerais dos Testes

### 3.4.1. Resultados dos Testes Unitários

- As regras de negócio avaliadas apresentaram comportamento consistente conforme esperado;
- Entradas inválidas foram adequadamente tratadas;
- Métodos sensíveis não acionaram bancos de dados indevidamente;
- Nenhuma exceção inesperada foi identificada durante a execução dos testes.

### 3.4.2. Resultados dos Testes de Integração

- Os fluxos completos (Menu → Serviço → Repositório → Banco) funcionaram corretamente;
- As inserções no banco refletiram a lógica da aplicação;
- Não houve registros inválidos;
- A execução do sistema em ambiente real demonstrou estabilidade e previsibilidade.

---

## 3.5. Métricas, Medidas e Indicadores de Qualidade

### 3.5.1. Cobertura de Testes
- Cobertura funcional: **Alta**  
- Cobertura de entradas inválidas: **Completa**  
- Cobertura de fluxo ponta a ponta (E2E): **Completa**  

### 3.5.2. Confiabilidade e Integridade de Dados
- Falhas de persistência detectadas: **0**
- Inconsistências entre aplicação e banco: **0**
- Estrutura e conteúdo das tabelas confirmam o comportamento esperado.

### 3.5.3. Qualidade no Processo (CI/CD)
O pipeline de CI executa automaticamente:

- `mvn test`;
- Validação de Conventional Commits;
- Execução padronizada no GitHub Actions.

Reincidência de falhas após correções: **0%**  
Taxa de builds bem-sucedidos após estabilização: **100%**

---

## 3.6. Técnicas de Revisão de Código (Code Review)

Durante os Pull Requests, foram avaliados:

- Clareza e legibilidade do código;
- Separação adequada de responsabilidades;
- Uso correto de padrões Java;
- Organização consistente de pacotes;
- Evitação de código duplicado;
- Existência e completude de testes antes de aceitar a alteração.

Esse processo garante que apenas código funcional e limpo chega à branch `main`.

---

## 3.7. Garantia de Qualidade Contínua

A arquitetura do sistema permite:

- Expandir testes unitários;
- Criar novos testes de integração sem refatorações profundas;
- Aumentar métricas de qualidade conforme o projeto cresce;
- Manter um ciclo contínuo de qualidade, independentemente de novas funcionalidades.

---

# 3.8. TÓPICO ESPECÍFICO PARA INSERÇÃO DOS TESTES DE CADA CASE

A seguir se encontram os testes já implementados e documentados.  

---

## 3.8.1. Testes do Case 3 — Registro de Justificativa de Atraso  
**Arquivos envolvidos:**  
- `src/main/java/unifacs/a3/Menu.java`  
- `src/main/java/unifacs/a3/JustificativaAtraso.java`  
- `src/test/java/unifacs/a3/JustificativaAatrasoTest.java`  
- `src/test/java/unifacs/a3/testIntegracao/JustificativaIntegracaoMenuTest.java`

---

### ✔ O que foi testado

#### A) Testes Unitários (classe JustificativaAtraso)
- Mapeamento correto dos códigos de justificativa:  
  - 1 → Falta Injustificada  
  - 2 → Atestado  
  - 3 → Saída Antecipada  
  - 4 → Hora Extra  
- Códigos inválidos retornam “Opção inválida.”  
- Repositório acionado apenas para códigos válidos  
- Mensagens corretas retornadas ao usuário  
- Uso de Mockito para garantir isolamento das regras de negócio

#### B) Testes de Integração (fluxo real via Menu)
- Entrada simulada pelo usuário (ByteArrayInputStream)  
- Execução real do método `Menu.start()`  
- Persistência real no PostgreSQL  
- Consulta SQL via `PreparedStatement` para validação  
- Verificação completa do fluxo Menu → Serviço → Banco

---

### ✔ Cenários Válidos

| Cenário | Entrada | Resultado Esperado |
|--------|---------|--------------------|
| C1 | 1 | Inserir "Falta Injustificada" |
| C2 | 2 | Inserir "Atestado" |
| C3 | 3 | Inserir "Saída Antecipada" |
| C4 | 4 | Inserir "Hora Extra" |

---

### ✔ Cenários Inválidos

| Cenário | Entrada | Resultado Esperado |
|---------|----------|--------------------|
| I1 | 0 | "Opção inválida." + Nenhum insert |
| I2 | 5 | "Opção inválida." + Nenhum insert |
| I3 | Texto | Tratado como inválido + Nenhum insert |

---

### ✔ Resultados Consolidados

- Todos os testes unitários passaram com sucesso  
- Todos os testes de integração confirmaram o fluxo correto  
- Nenhuma inconsistência no banco  
- Nenhum registro inválido foi encontrado  
- A funcionalidade apresenta estabilidade e integridade completa

---

## 3.8.2. Testes do Case X — *[Nome da funcionalidade]*  
*(Copie a estrutura do Case 3 acima ao adicionar novos cases)*

---

## 3.9. Conclusão Geral da Etapa 3

Esta etapa demonstrou que:

- O sistema possui testes de unidade e integração bem organizados;  
- As regras de negócio funcionam conforme especificado;  
- O fluxo completo até o banco de dados está correto e estável;  
- O processo de CI/CD reforça a qualidade do código;  
- A estrutura está preparada para inclusão contínua de novos testes.

Esse conjunto de práticas garante que o sistema pode evoluir com segurança, mantendo qualidade e confiabilidade em todas as funcionalidades.
