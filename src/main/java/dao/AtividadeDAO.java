package dao;

import model.Atividade;
import utils.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtividadeDAO {

    public void criarAtividade(Atividade atividade, int eventoID) throws SQLException {
        String sql = "INSERT INTO ATIVIDADE (evento_id, nome, descricao, data_realizacao, hora_inicio, hora_fim, limite_inscritos, vagas_disponiveis, tipo ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, eventoID);
            stmt.setString(2, atividade.getNome());
            stmt.setString(3, atividade.getDescricao());
            stmt.setString(4, atividade.getData_realizacao());
            stmt.setString(5, atividade.getHora_inicio());
            stmt.setString(6, atividade.getHora_fim());
            stmt.setInt(7, atividade.getLimite_inscritos());
            stmt.setInt(8, atividade.getLimite_inscritos()); 
            stmt.setString(9, atividade.getTipo());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    atividade.setId(rs.getInt(1));
                }
            }
        }
    }

    public Atividade buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Atividade WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Atividade(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("data_realizacao"),
                        rs.getString("hora_inicio"),
                        rs.getString("hora_fim"),
                        rs.getInt("limite_inscritos"),
                        rs.getInt("vagas_disponiveis"),
                        rs.getString("tipo")
                    );
                }
            }
        }
        return null;
    }

    public List<Atividade> listarPorEvento(int eventoID) throws SQLException {
        List<Atividade> atividades = new ArrayList<>();
        String sql = "SELECT * FROM Atividade WHERE evento_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, eventoID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Atividade atividade = new Atividade(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("data_realizacao"),
                        rs.getString("hora_inicio"),
                        rs.getString("hora_fim"),
                        rs.getInt("limite_inscritos"),
                        rs.getInt("vagas_disponiveis"),
                        rs.getString("tipo")
                    );
                    atividades.add(atividade);
                }
            }
        }
        return atividades;
    }

    public void atualizarAtividade(Atividade atividade) throws SQLException {
        String sql = "UPDATE Atividade SET nome = ?, descricao = ?, data_realizacao = ?, hora_inicio = ?, hora_fim = ?, limite_inscritos = ?, vagas_disponiveis = ?, tipo = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, atividade.getNome());
            stmt.setString(2, atividade.getDescricao());
            stmt.setString(3, atividade.getData_realizacao());
            stmt.setString(4, atividade.getHora_inicio());
            stmt.setString(5, atividade.getHora_fim());
            stmt.setInt(6, atividade.getLimite_inscritos());
            stmt.setInt(7, atividade.getVagas_disponivel());
            stmt.setString(8, atividade.getTipo());
            stmt.setInt(9, atividade.getId());

            stmt.executeUpdate();
        }
    }

    public void deletarAtividade(int id) throws SQLException {
        String sql = "DELETE FROM Atividade WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public boolean temVagasDisponiveis(int atividadeID) throws SQLException {
        String sql = "SELECT vagas_disponiveis FROM Atividade WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, atividadeID);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public String listarAtividadesFormatadas(int eventoID) throws SQLException {
        List<Atividade> atividades = listarPorEvento(eventoID);
        StringBuilder sb = new StringBuilder();

        if (atividades.isEmpty()) {
            sb.append("Nenhuma atividade cadastrada para este evento.");
        } else {
            sb.append("\n--- LISTA DE ATIVIDADES ---\n");
            sb.append(String.format("%-5s %-30s %-15s %-10s %-10s %-10s %-10s %-15s%n",
                    "ID", "Nome", "Data", "Início", "Fim", "Limite", "Disp.", "Tipo"));
            sb.append("---------------------------------------------------------------------------------------------------------\n");

            for (Atividade atividade : atividades) {
                sb.append(String.format("%-5d %-30s %-15s %-10s %-10s %-10d %-10d %-15s%n",
                        atividade.getId(),
                        atividade.getNome(),
                        atividade.getData_realizacao(),
                        atividade.getHora_inicio(),
                        atividade.getHora_fim(),
                        atividade.getLimite_inscritos(),
                        atividade.getVagas_disponivel(),
                        atividade.getTipo()));
            }
        }
        return sb.toString();
    }
}