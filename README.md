# 🎓 Sistema de Gerenciamento de Eventos Acadêmicos – IFNMG

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![SQLite](https://img.shields.io/badge/SQLite-3-blue)
![POO](https://img.shields.io/badge/POO-Aplicado-success)
![UML](https://img.shields.io/badge/UML-Diagramas-critical)

Aplicação desktop desenvolvida em Java, utilizando **Programação Orientada a Objetos**, com persistência de dados via **SQLite**. O sistema simula a gestão institucional de eventos acadêmicos para alunos, professores e profissionais externos, permitindo inscrições controladas, integração com atividades e visualização de relatórios administrativos.

---

## 📚 Contexto Acadêmico

Projeto desenvolvido como parte da disciplina **Programação Orientada a Objetos (POO)** do curso **Análise e Desenvolvimento de Sistemas – IFNMG**, com objetivo de demonstrar domínio técnico sobre:

- Encapsulamento, abstração, herança e polimorfismo
- Persistência com SQLite via JDBC
- Tratamento de exceções customizadas
- Separação de camadas com interfaces e DAOs
- Regras de negócio aplicadas ao fluxo de inscrições institucionais

---

## 🧠 Conceitos de POO Implementados

| Conceito           | Aplicação na Prática                                                                 |
|--------------------|--------------------------------------------------------------------------------------|
| **Encapsulamento** | Atributos privados com acesso via getters/setters (`User`, `Evento`, `Atividade`)   |
| **Herança**        | `Admin` e `Participante` estendem `User` com papel (`role`) diferenciado             |
| **Polimorfismo**   | Validação dinâmica por tipo de usuário em regras de negócio e interfaces             |
| **Abstração**      | Interfaces (`UserInterface`, `EventoInterface`, etc.) definem contratos claros       |
| **Exceções**       | Classes personalizadas como `EmailDuplicadoException`, `VagasEsgotadasException`, `ValorInvalidoException` |

---

## ⚙️ Funcionalidades Principais

### 👤 Autenticação e Cadastro
- Validação de e-mail (regex) e senha forte
- Criptografia segura via `BCrypt`
- Perfis com acesso: `ADMIN`, `ALUNO`, `PROFESSOR`, `PROFISSIONAL`

### 📅 Gestão de Eventos
- Criar, editar, deletar eventos
- Controle de vagas (total/disponível)
- Listagens formatadas com alinhamento textual

### 🗓️ Atividades Acadêmicas
- Cadastro vinculado ao evento
- Tipos permitidos: `PALESTRA`, `CURSO`, `SIMPOSIO`
- Controle de horários e limite de inscritos

### 📝 Inscrições
- Regras: vagas, pendência, recusas anteriores
- Atualização automática das vagas após confirmação
- Relatórios tabulados com JOIN e filtros

### 💰 Tabela de Valores
- Valores diferenciados por perfil (`ALUNO`, `PROFESSOR`, `PROFISSIONAL`)
- Interface para atualização segura com validação decimal
- Listagem ordenada com formatação monetária

---

## 📊 Modelagem de Dados

**Tabelas:**

- `User`
- `Evento`
- `evento_user`
- `Atividade`
- `atividade_user`
- `config_inscricao`

**Relacionamentos:**

- `User` ←→ `evento_user` ←→ `Evento`
- `Evento` ←→ `Atividade` ←→ `atividade_user` ←→ `User`

---

## 🔐 Segurança e Validações

- **Senhas criptografadas** com `BCrypt`
- **Controle de duplicidade** para e-mail e inscrições
- **Exceções especializadas** para cenários de negócio
- **Validações dinâmicas** com mensagens descritivas
- **Transações com rollback** em `InscricaoEventoDAO`

---

## 📄 Relatórios Gerados

- Inscrições pendentes por evento
- Eventos confirmados por usuário
- Todas as inscrições realizadas
- Atividades inscritas
- Valores financeiros por papel

Todos apresentados com tabelas alinhadas e ordenadas, legíveis para documentação administrativa.

---

## 💻 Tecnologias Utilizadas

- **Java 17+**
- **SQLite 3** (banco local)
- **JDBC**
- **Lombok** (anotações para getters/setters)
- **BCrypt** (autenticação)
- **Arquitetura por camadas**: `model`, `dao`, `interfaces`, `exceptions`, `utils`

---

## 📦 Dependências

```xml
<dependencies>
    <!-- SQLite -->
    <dependency>
      <groupId>org.xerial</groupId>
      <artifactId>sqlite-jdbc</artifactId>
      <version>3.42.0.0</version>
    </dependency>

    <!-- BCrypt -->
    <dependency>
      <groupId>org.mindrot</groupId>
      <artifactId>jbcrypt</artifactId>
      <version>0.4</version>
    </dependency>

    <!--- Lombok -->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.18.38</version>
      <scope>provided</scope>
    </dependency>
</dependencies>
