# 🎓 Sistema de Gerenciamento de Eventos Acadêmicos

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![SQLite](https://img.shields.io/badge/SQLite-3-blue)
![UML](https://img.shields.io/badge/UML-Diagramas-9cf)
![POO](https://img.shields.io/badge/POO-Avançado-success)

Solução completa para organização de eventos universitários, implementando os principais conceitos de Programação Orientada a Objetos com persistência em banco de dados.

## 🔍 Visão Geral

Sistema desktop desenvolvido para:

- Automatizar a gestão de eventos acadêmicos (palestras, cursos, simpósios)
- Controlar inscrições de participantes (alunos, professores, profissionais)
- Gerenciar pagamentos e confirmações
- Gerar relatórios administrativos

## 💻 Tecnologias e Conceitos Aplicados

### 🧠 Paradigma POO

- **Herança**: Hierarquia de usuários (Admin/Participante)
- **Polimorfismo**: Cálculo dinâmico de taxas por perfil
- **Encapsulamento**: Getters/Setters e classes controladoras
- **Abstração**: Classes base (Usuario, Evento)
- **Composição**: Evento → Atividades

### 🛢️ Persistência de Dados

- **SQLite**: Banco relacional embutido
- **JDBC**: Conexão com banco de dados
- **DAOs**: Padrão de acesso a dados para cada entidade

### 🚨 Tratamento de Exceções

- Customizadas:
  - `LimiteVagasException`
  - `InscricaoDuplicadaException`
  - `PagamentoInvalidoException`
- Blocos try-catch em operações críticas

### 📊 Modelagem

- **Diagramas UML**:
  - Casos de uso (Admin/Participante)
  - Diagrama de classes completo
  - Modelo relacional do banco

## ⚙️ Funcionalidades-Chave

| Área           | Recursos                                                                  |
| -------------- | ------------------------------------------------------------------------- |
| **Eventos**    | Criação, edição, exclusão, listagem de participantes do evento            |
| **Atividades** | Vagas limitadas, tipos específicos (palestra/curso), vinculação a eventos |
| **Pagamentos** | Valores por perfil, confirmação manual, histórico                         |
| **Relatórios** | Participação por evento, status financeiro                                |

## 📦 Dependências Principais

```xml
<dependencies>
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.42.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.28</version>
    </dependency>
</dependencies>
```
