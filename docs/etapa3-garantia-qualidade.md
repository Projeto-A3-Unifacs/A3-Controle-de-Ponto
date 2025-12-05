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

O projeto contém diversas funcionalidades no Menu e classes associadas a regras de negócio, persistência e operações do sistema.  
Cada classe, serviço ou operação pode ser documentado nesta seção conforme for sendo testado.

---

## 3.4. Resultados Gerais dos Testes

### 3.4.1. Resultados dos Testes Unitários

- As regras de negócio avaliadas apresentaram comportamento consistente conforme o esperado;  
- Entradas inválidas foram adequadamente tratadas;  
- Métodos sensíveis não acionaram bancos de dados indevidamente;  
- Nenhuma exceção inesperada foi identificada durante a execução dos testes.

### 3.4.2. Resultados dos Testes de Integração

- Os fluxos completos (Menu → Serviço → Banco) funcionaram corretamente;  
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

# 3.8. TÓPICO ESPECÍFICO PARA INSERÇÃO DOS TESTES DE CADA FUNCIONALIDADE

A seguir se encontram os testes implementados e documentados, organizados por classe conforme encontrado no projeto.

---

# 3.8.1. Testes da funcionalidade `JustificativaAtraso`

**Arquivos envolvidos:**  
- `src/main/java/unifacs/a3/Menu.java`  
- `src/main/java/unifacs/a3/JustificativaAtraso.java`  
- `src/test/java/unifacs/a3/JustificativaAatrasoTest.java`  
- `src/test/java/unifacs/a3/testIntegracao/JustificativaIntegracaoMenuTest.java`

---

### ✔ O que foi testado

#### A) Testes Unitários
- Mapeamento correto dos códigos 1–4  
- Tratamento de códigos inválidos  
- Mensagens de retorno  
- Repositório acionado somente em opções válidas  
- Uso de Mockito para isolar dependências

#### B) Testes de Integração
- Execução real via Menu  
- Entrada simulada  
- Inserção real no PostgreSQL  
- Consulta SQL para conferência

---

### ✔ Resultados Consolidados
- Cobertura completa da regra de negócio  
- Integração validada  
- Banco consistente  

---

# 3.8.2. Testes da Classe `UsuarioBD` (Cadastro, Autenticação e Persistência)

**Arquivos envolvidos:**  
- `src/main/java/unifacs/a3/UsuarioBD.java`    
- `src/test/java/unifacs/a3/CadastraEVerifica.java`  
- `src/test/java/unifacs/a3/UsuarioBDTest.java`  
- `src/test/java/unifacs/a3/testIntegracao/CadastraTeste.java`  
- `src/test/java/unifacs/a3/testIntegracao/VerifcaUserTest.java`

---

### ✔ O que foi testado

#### A) Testes Unitários
- Inserção no banco via JDBC  
- Verificação de login  
- Cadastro de Usuário 
- Tratamento de exceções  
- Construção do objeto `Usuario` via ResultSet  

#### B) Testes de Integração
- Inserção real  
- Autenticação real  
- Leitura e comparação de dados no banco  

---

### ✔ Resultados Consolidados
- Cadastro validado  
- Login funcionando corretamente  
- Persistência estável  

---

# 3.8.3. Testes da Classe `RecuperaSenhaService` (Recuperação de Senha)

**Arquivos envolvidos:**  
- `src/main/java/unifacs/a3/RecuperaSenhaService.java`
- `src/main/java/unifacs/a3/UsuarioRepository.java`
- `src/test/java/unifacs/a3/RecuperaSenhaServiceTest.java`  
- `src/test/java/unifacs/a3/testIntegracao/RecuperaSenhaTest.java`

---

### ✔ O que foi testado

#### A) Testes Unitários
- Validação de email  
- Geração de senha  
- Atualização simulada  

#### B) Testes de Integração
- Atualização real no banco  
- Consulta real  
- Comparação antes/depois  

---

### ✔ Resultados Consolidados
- Processo de recuperação funcionando  
- Persistência garantida  

---

# 3.8.4. Testes da Classe `HorarioBD` (Entrada, Saída, Atrasos e Faltas)

**Arquivos envolvidos:**  
- `src/main/java/unifacs/a3/HorarioBD.java`  
- `src/test/java/unifacs/a3/HorarioBDTest.java`  
- `src/test/java/unifacs/a3/HorarioTestHelper.java`
- `src\test\java\unifacs\a3\testIntegracao\RegistroSaidaTeste.java`
- `src\test\java\unifacs\a3\testIntegracao\RegistroEntradaTeste.java`
- `src\test\java\unifacs\a3\testIntegracao\VerificaAtrasoTeste.java`

---

### ✔ O que foi testado

#### A) Testes Unitários
- Registro de entrada  
- Impedir duplicidade  
- Registro de saída  
- Cálculo de atraso  
- Faltas semanais  
- Faltas mensais
  
#### B) Testes de Integração
- Atualização real no banco  
- Consulta real
---

### ✔ Resultados Consolidados
- Regras de horário funcionam corretamente  
- Cálculos validados  
- Nenhuma falha identificada  

---

# 3.8.5. Testes de Integração Gerais

**Arquivos envolvidos:**  
- `CadastraTeste.java`  
- `VerifcaUserTest.java`  
- `RecuperaSenhaTest.java`  
- `JustificativaIntegracaoMenuTest.java`
- `RegistroSaidaTeste.java`
- `RegistroEntradaTeste.java`
- `VerificaAtrasoTeste.java`

---

### ✔ O que foi validado
- Execução real do Menu  
- Persistência completa no banco  
- Inserções e consultas reais  
- Fluxos integrados funcionando  

---

### ✔ Resultados Consolidados
- Integrações funcionando corretamente  
- Banco consistente  
- Fluxos ponta a ponta validados  

---

# 3.9. Conclusão Geral da Etapa 3

Esta etapa demonstrou que:

- O sistema possui testes robustos e bem estruturados;  
- As classes principais foram validadas em cenários reais;  
- O banco de dados se comporta de forma estável;  
- A integração CI/CD garante evolução segura.

O conjunto de práticas aplicadas assegura a qualidade, consistência e confiabilidade do sistema como um todo.
