package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {

    private static final String URL = "jdbc:sqlite:src/main/resources/eventos_academicos.db";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado", e);
        }
    }

    public static void criarTabelas() {
        String sqlUsuarios = """
        CREATE TABLE IF NOT EXISTS User (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nome TEXT NOT NULL,
            email TEXT UNIQUE NOT NULL,
            senha TEXT NOT NULL,
            role TEXT CHECK(role IN ('ADMIN', 'ALUNO', 'PROFESSOR', 'PROFISSIONAL')),
            created_at DATETIME DEFAULT CURRENT_TIMESTAMP
        );
        """;

        String sqlEventos = """
        CREATE TABLE IF NOT EXISTS Evento (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nome TEXT NOT NULL,
            descricao TEXT,
            data_inicio DATETIME NOT NULL,
            data_fim DATETIME NOT NULL,
            vagas_total INTEGER,
            vagas_disponivel INTEGER
        );
        """;

        String sqlEvento_user = """
        CREATE TABLE IF NOT EXISTS evento_user (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            usuario_id INTEGER NOT NULL,
            evento_id INTEGER NOT NULL,
            data_inscricao DATETIME DEFAULT CURRENT_TIMESTAMP,
            status_pagamento TEXT DEFAULT 'PENDENTE',
            FOREIGN KEY (usuario_id) REFERENCES User(id),
            FOREIGN KEY (evento_id) REFERENCES Evento(id),
            UNIQUE (usuario_id, evento_id)
        );
        """;

        String sqlAtividade = """
        CREATE TABLE IF NOT EXISTS Atividade (    
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            evento_id INTEGER NOT NULL,
            nome TEXT NOT NULL,
            descricao TEXT,
            data_realizacao DATETIME NOT NULL,
            hora_inicio TIME NOT NULL,
            hora_fim TIME NOT NULL,
            limite_inscritos INTEGER,
            vagas_disponiveis INTEGER,
            tipo TEXT CHECK(tipo IN ('PALESTRA', 'SIMPOSIO', 'CURSO')),
            FOREIGN KEY (evento_id) REFERENCES Evento(id)
        );
        """;

        String sqlAtividade_user = """
        CREATE TABLE IF NOT EXISTS atividade_user (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            usuario_id INTEGER NOT NULL,
            atividade_id INTEGER NOT NULL,
            data_inscricao DATETIME DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (usuario_id) REFERENCES User(id),
            FOREIGN KEY (atividade_id) REFERENCES Atividade(id),
            UNIQUE (usuario_id, atividade_id)
        );
        """;

        String sqlConfigInscricao = """
        CREATE TABLE IF NOT EXISTS config_inscricao (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            role TEXT NOT NULL UNIQUE CHECK(role IN ('ALUNO', 'PROFESSOR', 'PROFISSIONAL')),
            valor REAL NOT NULL,
            created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
            updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
        );
        """;

        String sqlInsertValores = """
        INSERT OR IGNORE INTO config_inscricao (role, valor) VALUES 
            ('ALUNO', 10.0),
            ('PROFESSOR', 50.0),
            ('PROFISSIONAL', 100.0);
        """;

        String deleteUsers = """
        DROP TABLE IF EXISTS User;
        """;

        String deleteEventos = """
        DROP TABLE IF EXISTS Evento;
        """;

        String deleteEvento_user = """
        DROP TABLE IF EXISTS evento_user;
        """;

        String deleteAtividade = """
        DROP TABLE IF EXISTS Atividade;
        """;

        String deleteConfigInscricao = """
        DROP TABLE IF EXISTS config_inscricao;
        """;

        try  {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

            stmt.execute(sqlUsuarios);
            stmt.execute(sqlEventos);
            stmt.execute(sqlEvento_user);
            stmt.execute(sqlAtividade);
            stmt.execute(sqlAtividade_user);

            stmt.execute(sqlConfigInscricao);
            stmt.execute(sqlInsertValores);

//            stmt.execute(deleteUsers);
//            stmt.execute(deleteEventos);
//            stmt.execute(deleteEvento_user);
//            stmt.execute(deleteAtividade);
//            stmt.execute(deleteConfigInscricao);

            System.out.println("Tabelas criadas com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        } finally {
            closeConnection(null);
        }
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
        }
    }

}