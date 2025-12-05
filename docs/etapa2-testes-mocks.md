# Etapa 2 – Desenvolvimento e Testes Iniciais com Mocks e Testes de Integração Reais

## 2.1. Objetivo da Etapa

A Etapa 2 tem como objetivo aplicar técnicas de **testes unitários**, **testes de integração** e **validação das funcionalidades**, garantindo que as classes implementadas estejam corretas, estáveis e integradas ao banco de dados real utilizado pelo sistema.

Além disso, esta etapa demonstra domínio em ferramentas e boas práticas de testes, incluindo:

- JUnit 5
- Mockito
- Testes de integração com PostgreSQL real
- Isolamento da regra de negócio
- Verificação de persistência e consistência de dados

---

## 2.2. Abrangência dos Testes

Nesta etapa, o projeto foi submetido a testes em **todas as classes e funcionalidades alteradas**, assegurando alinhamento total entre:

- Regras de negócio  
- Serviços  
- Camada de dados (PostgreSQL)  
- Interação via Menu  

As funcionalidades cobertas nos testes incluem:

- Registro de justificativa de atraso  
- Validação de usuário  
- Registro de ponto  
- Cadastro e recuperação de usuário  
- Verificação de atrasos e faltas  
- Fluxos principais do Menu  
- Persistência e leitura no banco PostgreSQL

---

## 2.3. Testes Unitários com Mockito

Os testes unitários foram desenvolvidos com foco na **regra de negócio**, isolando as classes de dependências externas. Para isso, utilizou-se:

- Mockito para criação de *mocks* dos repositórios (`UsuarioBD`, `JustificativaBD`, `HorarioBD`, etc.)
- JUnit 5 para organização e execução das suites de teste

### Resultados dos testes unitários

Os testes demonstraram que:

- Os serviços funcionam independentemente do banco de dados  
- Códigos e entradas inválidas são corretamente tratados  
- As mensagens retornadas ao usuário são consistentes  
- Chamadas ao repositório só ocorrem quando apropriado  
- A lógica das classes funciona de forma determinística  

---

## 2.4. Testes de Integração com PostgreSQL Real

Um diferencial importante deste projeto é a realização de **testes de integração reais utilizando o banco PostgreSQL**, que é o banco de dados oficial da aplicação.

### 2.4.1. Estratégia dos testes de integração

Os testes de integração incluem:

1. Conexão direta com o PostgreSQL  
2. Criação de registros reais para teste  
3. Execução completa das funcionalidades via classes de serviço ou Menu  
4. Simulação do usuário final com entrada simulada via `ByteArrayInputStream`  
5. Leitura e validação dos dados persistidos via `PreparedStatement` e `ResultSet`  
6. Comparação entre entrada → lógica → persistência → retorno  

### 2.4.2. Classe de Teste de Integração

Exemplo:

- `testIntegracao/JustificativaIntegracaoMenuTest.java`  
  - Simula entrada do usuário  
  - Executa `Menu.start(usuario)`  
  - Persiste dados via `JustificativaBD`  
  - Valida o registro no banco PostgreSQL real

### 2.4.3. Benefícios deste tipo de teste

- Testes iguais ao ambiente real de produção  
- Detecta falhas de SQL, schemas, migrações, colunas ausentes ou inconsistentes  
- Garante que o fluxo completo (Menu → Serviço → BD) está funcional  
- Aumenta a confiabilidade geral do sistema  

---

## 2.5. Testes em Todas as Funcionalidades Alteradas

Além do case 3, foram testados:

### 2.5.1. Usuário (Usuario / UsuarioBD)

- Criação e validação de usuário  
- Login  
- Recuperação de senha  
- Consulta de dados  

### 2.5.2. Registro de Ponto (HorarioBD)

- Registro de horário  
- Cálculo e verificação de atraso  
- Registro de faltas  
- Atualização de horários  

### 2.5.3. Menu (Menu.java)

Todos os cases foram testados:

- Case 1 – Registrar ponto  
- Case 2 – Cadastrar Funcionário
- Case 3 – Registrar justificativa de atraso  
- Case 4 – Verificar faltas  
- Case 5 – Verificar atrasos 

### 2.5.4. Serviços auxiliares

- Validação de entradas  
- Conversão de dados  
- Lógica de tratamento de mensagens  

---

## 2.6. Conclusão da Etapa 2

A Etapa 2 permitiu elevar o nível de qualidade e confiabilidade do projeto, garantindo:

- Testes completos em todas as funcionalidades alteradas  
- Validação real das operações no banco PostgreSQL  
- Ambiente de testes coerente com produção  
- Detecção precoce de falhas  
- Aumento da robustez do sistema  
- Preparação sólida para as etapas seguintes de Garantia de Qualidade  

Combinando testes unitários e testes de integração reais, o projeto atingiu um padrão profissional de confiabilidade e boas práticas de desenvolvimento, fortalecendo a arquitetura e reduzindo significativamente a chance de regressões.
