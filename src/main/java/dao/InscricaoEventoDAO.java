package dao;

import exceptions.InscricaoNaoPermitidaException;
import exceptions.InscricaoPendenteException;
import exceptions.VagasEsgotadasException;
import interfaces.InscricaoEventoInterface;
import exceptions.UsuarioJaInscritoException;
import utils.ConnectionFactory;
import java.sql.*;

public class InscricaoEventoDAO implements InscricaoEventoInterface {

    @Override
    public void inscreverUsuario(int userID, int eventoID)
            throws SQLException, VagasEsgotadasException, UsuarioJaInscritoException, InscricaoPendenteException, InscricaoNaoPermitidaException {

        EventoDAO eventoDAO = new EventoDAO();

        if (!eventoDAO.temVagasDisponiveis(eventoID)) {
            throw new VagasEsgotadasException("Não há vagas disponíveis para este evento!");
        }

        if (usuarioTemInscricaoPendente(userID, eventoID)) {
            throw new InscricaoPendenteException("Você já tem uma inscrição pendente para este evento!");
        }

        if (usuarioJaInscrito(userID, eventoID)) {
            throw new UsuarioJaInscritoException("Você já está inscrito neste evento!");
        }

        if (usuarioJaRecusado(userID, eventoID)) {
            throw new InscricaoNaoPermitidaException("Você já teve sua inscrição recusada para este evento!");
        }

        if (usuarioJaPedente(userID, eventoID)) {
            throw new InscricaoNaoPermitidaException("Sua inscrição para este evento ainda está pendente!");
        }

        String sql = "INSERT INTO evento_user (usuario_id, evento_id, status_pagamento) VALUES (?, ?, 'PENDENTE')";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            stmt.setInt(2, eventoID);
            stmt.executeUpdate();
        }
    }

    @Override
    public void atualizarStatusInscricao(int inscricaoID, String status) throws SQLException {
        if (!status.equals("CONFIRMADO") && !status.equals("RECUSADO") && !status.equals("PENDENTE")) {
            throw new SQLException("Status de inscrição inválido");
        }

        String sql = "UPDATE evento_user SET status_pagamento = ? WHERE id = ?";

        String atualizaVagas = "";
        if (status.equals("CONFIRMADO")) {
            atualizaVagas = "UPDATE Evento SET vagas_disponivel = vagas_disponivel - 1 WHERE id = " +
                    "(SELECT evento_id FROM evento_user WHERE id = ?)";
        }

        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement stmtStatus = conn.prepareStatement(sql)) {
                stmtStatus.setString(1, status);
                stmtStatus.setInt(2, inscricaoID);
                stmtStatus.executeUpdate();
            }

            if (!atualizaVagas.isEmpty()) {
                try (PreparedStatement stmtVagas = conn.prepareStatement(atualizaVagas)) {
                    stmtVagas.setInt(1, inscricaoID);
                    stmtVagas.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    @Override
    public String listarInscricoesPendentes() throws SQLException {
        String sql = """
        SELECT eu.id, u.nome as usuario_nome, u.email, e.nome as evento_nome, 
               e.data_inicio, e.data_fim, u.role as tipo_usuario
        FROM evento_user eu
        JOIN User u ON eu.usuario_id = u.id
        JOIN Evento e ON eu.evento_id = e.id
        WHERE eu.status_pagamento = 'PENDENTE'
        ORDER BY e.data_inicio, u.nome
        """;

        StringBuilder sb = new StringBuilder();

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (!rs.isBeforeFirst()) {
                return "\nNão há inscrições pendentes de confirmação.";
            }

            sb.append("\n--- INSCRIÇÕES PENDENTES ---\n");
            sb.append(String.format("%-5s %-20s %-20s %-30s %-15s %-15s%n",
                    "ID", "Usuário", "Tipo", "Evento", "Data Início", "Data Fim"));
            sb.append("------------------------------------------------------------------------------------------------------------\n");

            while (rs.next()) {
                sb.append(String.format("%-5d %-20s %-20s %-30s %-15s %-15s%n",
                        rs.getInt("id"),
                        rs.getString("usuario_nome"),
                        rs.getString("tipo_usuario"),
                        rs.getString("evento_nome"),
                        rs.getString("data_inicio"),
                        rs.getString("data_fim")));
            }
        }

        return sb.toString();
    }

    private boolean usuarioJaInscrito(int userID, int eventoID) throws SQLException {
        String sql = """
        SELECT COUNT(*) FROM evento_user 
        WHERE usuario_id = ? AND evento_id = ? 
        AND status_pagamento IN ('PENDENTE', 'CONFIRMADO')
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            stmt.setInt(2, eventoID);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    @Override
    public boolean usuarioTemInscricaoPendente(int userID, int eventoID) throws SQLException {
        String sql = """
        SELECT COUNT(*) FROM evento_user 
        WHERE usuario_id = ? AND evento_id = ? 
        AND status_pagamento = 'PENDENTE'
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            stmt.setInt(2, eventoID);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    @Override
    public boolean usuarioJaRecusado(int userID, int eventoID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM evento_user WHERE usuario_id = ? AND evento_id = ? AND status_pagamento = 'RECUSADO'";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, eventoID);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    @Override
    public boolean usuarioJaPedente(int userID, int eventoID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM evento_user WHERE usuario_id = ? AND evento_id = ? AND status_pagamento = 'PENDENTE'";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setInt(2, eventoID);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    @Override
    public void cancelarInscricao(int userID, int eventoID) throws SQLException {
        String sql = "DELETE FROM evento_user WHERE usuario_id = ? AND evento_id = ?";
        String atualizaVagas = "UPDATE Evento SET vagas_disponivel = vagas_disponivel + 1 WHERE id = ?";

        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement stmtDelete = conn.prepareStatement(sql);
                 PreparedStatement stmtVagas = conn.prepareStatement(atualizaVagas)) {

                stmtDelete.setInt(1, userID);
                stmtDelete.setInt(2, eventoID);
                int affectedRows = stmtDelete.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Inscrição não encontrada");
                }

                stmtVagas.setInt(1, eventoID);
                stmtVagas.executeUpdate();

                conn.commit();
            }
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    @Override
    public String listarEventosConfirmadosDoUsuario(int userID) throws SQLException {
        String sql = """
        SELECT e.id, e.nome, e.descricao, e.data_inicio, e.data_fim, 
               e.vagas_total, e.vagas_disponivel, eu.data_inscricao
        FROM evento_user eu
        JOIN Evento e ON eu.evento_id = e.id
        WHERE eu.usuario_id = ? AND eu.status_pagamento = 'CONFIRMADO'
        ORDER BY e.data_inicio
        """;

        StringBuilder sb = new StringBuilder();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                return "\nVocê não está inscrito em nenhum evento confirmado.";
            }

            sb.append("\n--- SEUS EVENTOS CONFIRMADOS ---\n");
            sb.append(String.format("%-5s %-30s %-15s %-15s %-10s %-20s%n",
                    "ID", "Evento", "Data Início", "Data Fim", "Vagas", "Data Inscrição"));
            sb.append("-------------------------------------------------------------------------------------------\n");

            while (rs.next()) {
                sb.append(String.format("%-5d %-30s %-15s %-15s %-10d %-20s%n",
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("data_inicio"),
                        rs.getString("data_fim"),
                        rs.getInt("vagas_total"),
                        rs.getString("data_inscricao")));
            }
        }

        return sb.toString();
    }

}