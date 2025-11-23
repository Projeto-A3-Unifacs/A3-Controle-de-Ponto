# A3-Controle-de-Ponto
Com base em tudo o que analisamos e corrigimos no seu projeto (estrutura das classes, banco de dados Supabase, testes unitários com Mockito e padrão de conexão), preparei uma apresentação completa em **Markdown**.

Você pode usar esse texto no **README.md** do seu repositório GitHub ou como base para slides de apresentação para o professor.

-----

# 🕒 Sistema de Controle de Ponto e Frequência (A3)

Um sistema robusto desenvolvido em **Java** para gerenciamento eficiente de jornada de trabalho, controle de frequência e justificativas de ausência. O projeto foca na integridade dos dados e na arquitetura limpa de conexão com banco de dados em nuvem.

-----

## 🎯 Objetivo do Projeto

Desenvolver uma aplicação funcional para automatizar o registro de entrada e saída de funcionários, permitindo cálculos automáticos de faltas, verificação de atrasos e gestão administrativa de usuários, utilizando persistência de dados real via **PostgreSQL**.

-----

## 🚀 Funcionalidades Principais

### 👤 Gestão de Usuários

  * **Cadastro e Login:** Sistema de autenticação seguro com verificação de credenciais no banco.
  * **Recuperação de Senha:** Funcionalidade para redefinição de acesso.

### ⏰ Controle de Jornada

  * **Registro de Ponto:** Marcação precisa de **Entrada** e **Saída** (armazenando `Timestamp` no banco).
  * **Detecção de Atrasos:** O sistema identifica automaticamente registros realizados após o horário limite (ex: 08:00).

### 📊 Relatórios e Métricas

  * **Contador de Faltas:** Algoritmo inteligente que calcula faltas semanais e mensais, desconsiderando fins de semana (Sáb/Dom).
  * **Histórico de Atrasos:** Listagem de dias em que o colaborador excedeu o horário de entrada.

### 📝 Justificativas Administrativas

  * Registro formal de ocorrências com tipificação:
      * Falta Injustificada
      * Atestado Médico
      * Saída Antecipada
      * Hora Extra

-----

## 🛠️ Stack Tecnológica

  * **Linguagem:** Java (JDK 21)
  * **Banco de Dados:** PostgreSQL (Hospedado no **Supabase**)
  * **Conectividade:** JDBC (Java Database Connectivity)
  * **Testes Unitários:** JUnit 5 + Mockito
  * **Controle de Versão:** Git (Padrão Conventional Commits)

-----

## 🏗️ Arquitetura e Destaques Técnicos

O projeto foi refatorado para garantir alta performance e estabilidade, utilizando padrões de projeto modernos:

### 1\. Padrão Singleton (Connection Manager)

Implementamos um gerenciamento centralizado de conexões para evitar o erro *`FATAL: Max client connections reached`*.

  * **Antes:** Múltiplas conexões abertas e fechadas incorretamente.
  * **Agora:** Uma única conexão é instanciada no início da aplicação (`App.java`) e injetada via dependência nas classes de serviço (`UsuarioBD`, `HorarioBD`).

### 2\. Injeção de Dependência e Testabilidade

As classes de acesso a dados (DAO) foram reestruturadas para aceitar a conexão via construtor. Isso permitiu a criação de uma camada de testes robusta:

  * **Mocks:** Uso do Mockito para simular o banco de dados.
  * **Isolamento:** Testes unitários rodam sem precisar de conexão real com a internet ou banco de dados.

### 3\. Conexão Otimizada

Configuração ajustada para **Session Mode** (Porta 5432) no Supabase, garantindo compatibilidade total com o driver JDBC do PostgreSQL.

-----

## 🧪 Exemplo de Código (Testes)

O sistema conta com cobertura de testes para garantir a lógica de negócios:

```java
@Test
void deveRetornarUsuario() throws Exception {
    // Mock do Banco de Dados
    UsuarioBD usuarioBDMock = mock(UsuarioBD.class);
    Usuario esperado = new Usuario("Teste", 1234);

    when(usuarioBDMock.verificaUser(esperado)).thenReturn(esperado);

    // Teste isolado da lógica
    Usuario resultado = CadastraEVerifica.verificaUser(esperado, usuarioBDMock);
    assertNotNull(resultado);
}
```

-----

## 🏁 Como Executar

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/A3-Controle-de-Ponto.git
    ```
2.  **Configure o Banco:**
    Certifique-se de que as tabelas `usuario`, `horarios` e `justificativa` estão criadas no seu Supabase.
3.  **Execute a Aplicação:**
    Rode a classe `App.java` na sua IDE favorita (VS Code / Eclipse / IntelliJ).

-----

## 👥 Autores (Unifacs - A3)
  * Amanda Café Matos Costa
  * Daniela Silva de Jesus
  * Larissa Aparecida Bairon da Silva Sena
  * Pedro Arthur Leão Valente
  * Pedro Henrique Santos Borges
  * Victor Elísio dos Santos Silva

-----

*Projeto desenvolvido para a disciplina ministrada pelo Professor Thiago Dotto Fiuza Neves.*
