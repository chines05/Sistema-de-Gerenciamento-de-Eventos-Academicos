package dao;

import exceptions.InscricaoNaoPermitidaException;
import exceptions.InscricaoPendenteException;
import exceptions.VagasEsgotadasException;
import interfaces.InscricaoAtividadeInterface;
import exceptions.UsuarioJaInscritoException;
import utils.ConnectionFactory;
import java.sql.*;

public class InscricaoAtividadeDAO implements InscricaoAtividadeInterface {

    @Override
    public void inscreverUsuario(int userID, int AtividadeID)
            throws SQLException, VagasEsgotadasException, UsuarioJaInscritoException, InscricaoPendenteException, InscricaoNaoPermitidaException {

        AtividadeDAO atividadeDAO = new AtividadeDAO();

        if (!atividadeDAO.temVagasDisponiveis(AtividadeID)) {
            throw new VagasEsgotadasException("Não há vagas disponíveis para esta atividade!");
        }

        if (usuarioJaInscrito(userID, AtividadeID)) {
            throw new UsuarioJaInscritoException("Você já está inscrito nesta atividade!");
        }

        String sql = "INSERT INTO atividade_user (usuario_id, atividade_id) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            stmt.setInt(2, AtividadeID);
            stmt.executeUpdate();
        }

        String sql2 = "UPDATE atividade SET vagas_disponiveis = vagas_disponiveis - 1 WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql2)) {

            stmt.setInt(1, AtividadeID);
            stmt.executeUpdate();
        }
    }

    private boolean usuarioJaInscrito(int userID, int AtividadeID) throws SQLException {
        String sql = """
        SELECT COUNT(*) FROM atividade_user 
        WHERE usuario_id = ? AND atividade_id = ? 
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            stmt.setInt(2, AtividadeID);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    @Override
    public String listarAtividadesInscritas(int userID) throws SQLException {
        String sql = """
        SELECT a.id, a.nome, a.descricao, a.data_realizacao, a.hora_inicio, a.hora_fim, 
               a.limite_inscritos, a.vagas_disponiveis, au.data_inscricao
        FROM atividade_user au
        JOIN Atividade a ON au.atividade_id = a.id
        WHERE au.usuario_id = ?
        ORDER BY a.data_realizacao
        """;

        StringBuilder sb = new StringBuilder();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                return "\n Vocé não está inscrito em nenhuma atividade.";
            }

            sb.append("\n--- ATIVIDADES INSCRITAS ---\n");
            sb.append(String.format("%-5s %-30s %-15s %-15s %-10s %-20s %-10s %n",
                    "ID", "Atividade", "Data Realização", "Hora Início", "Hora Fim", "Limite Inscritos", "Data Inscrição"));
            sb.append("--------------------------------------------------------------------------------------------------------------------\n");

            while (rs.next()) {
                sb.append(String.format("%-5d %-30s %-20s %-15s %-15s %-10s %-20s %n",
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("data_realizacao"),
                        rs.getString("hora_inicio"),
                        rs.getString("hora_fim"),
                        rs.getInt("vagas_disponiveis"),
                        rs.getString("data_inscricao")));
            }
        }

        return sb.toString();
    }

}