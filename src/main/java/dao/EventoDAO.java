package dao;

import model.Evento;
import utils.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import interfaces.EventoInterface;

public class EventoDAO implements EventoInterface {

    @Override
    public void criarEvento(Evento evento) throws SQLException {
        String sql = "INSERT INTO Evento (nome, descricao, data_inicio, data_fim, vagas_total, vagas_disponivel) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());
            stmt.setString(3, evento.getDataInicio());
            stmt.setString(4, evento.getDataFim());
            stmt.setInt(5, evento.getVagasTotal());
            stmt.setInt(6, evento.getVagasDisponivel());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    evento.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public Evento buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Evento WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Evento(
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getString("data_inicio"),
                            rs.getString("data_fim"),
                            rs.getInt("vagas_total"),
                            rs.getInt("vagas_disponivel")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Evento> listarTodos() throws SQLException {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM Evento";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Evento evento = new Evento(
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("data_inicio"),
                        rs.getString("data_fim"),
                        rs.getInt("vagas_total"),
                        rs.getInt("vagas_disponivel")
                );
                evento.setId(rs.getInt("id"));
                eventos.add(evento);
            }
        }
        return eventos;
    }

    @Override
    public String listarEventosFormatados() throws SQLException {
        List<Evento> eventos = listarTodos();
        StringBuilder sb = new StringBuilder();

        if (eventos.isEmpty()) {
            sb.append("Nenhum evento cadastrado.");
        } else {
            sb.append("\n--- LISTA DE EVENTOS ---\n");
            sb.append(String.format("%-5s %-50s %-15s %-15s %-10s %-10s%n",
                    "ID", "Nome", "Data Início", "Data Fim", "Vagas", "Disp."));
            sb.append("---------------------------------------------------------------------------------------------------------\n");

            for (Evento evento : eventos) {
                sb.append(String.format("%-5d %-50s %-15s %-15s %-10d %-10d%n",
                        evento.getId(),
                        evento.getNome(),
                        evento.getDataInicio(),
                        evento.getDataFim(),
                        evento.getVagasTotal(),
                        evento.getVagasDisponivel()));
            }
        }
        return sb.toString();
    }

    @Override
    public void editarEvento(Evento evento) throws SQLException {
        String sql = """
            UPDATE Evento 
            SET nome = ?, descricao = ?, data_inicio = ?, data_fim = ?
            WHERE id = ?
            """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());
            stmt.setString(3, evento.getDataInicio());
            stmt.setString(4, evento.getDataFim());
            stmt.setInt(5, evento.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void deletarEvento(int id) throws SQLException {
        String sql = "DELETE FROM Evento WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public boolean temVagasDisponiveis(int eventoID) throws SQLException {
        String sql = "SELECT vagas_disponivel FROM Evento WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, eventoID);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}