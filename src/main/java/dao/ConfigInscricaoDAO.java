package dao;

import exceptions.ValorInvalidoException;
import utils.ConnectionFactory;
import java.sql.*;

public class ConfigInscricaoDAO {

    public double getValorInscricao(String role) throws SQLException {
        String sql = "SELECT valor FROM config_inscricao WHERE role = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, role);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("valor");
                }
                throw new SQLException("Tipo de participante não encontrado: " + role);
            }
        }
    }

    public void atualizarValorInscricao(String role, String valorInput) throws SQLException, ValorInvalidoException {
        role = role.toUpperCase();
        if (!role.equals("ALUNO") && !role.equals("PROFESSOR") && !role.equals("PROFISSIONAL")) {
            throw new IllegalArgumentException("Tipo de participante inválido: " + role);
        }

        double novoValor;
        try {
            String valorFormatado = valorInput.replace(",", ".");
            novoValor = Double.parseDouble(valorFormatado);

            if (novoValor <= 0) {
                throw new ValorInvalidoException("O valor deve ser maior que zero.");
            }

            novoValor = Math.round(novoValor * 100.0) / 100.0;

        } catch (NumberFormatException e) {
            throw new ValorInvalidoException("Valor inválido. Digite um número válido (ex: 50.00 ou 50,00)");
        }

        String sql = "UPDATE config_inscricao SET valor = ?, updated_at = CURRENT_TIMESTAMP WHERE role = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, novoValor);
            stmt.setString(2, role);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Tipo de participante não encontrado: " + role);
            }
        }
    }

    public String listarValoresFormatado() throws SQLException {
        String sql = "SELECT role, valor FROM config_inscricao ORDER BY valor DESC";

        StringBuilder sb = new StringBuilder();
        sb.append("\n--- VALORES DE INSCRIÇÃO ---\n");
        sb.append(String.format("%-15s %-15s%n", "TIPO", "VALOR"));
        sb.append("---------------------------\n");

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                sb.append(String.format("%-15s R$%-15.2f%n",
                        rs.getString("role"),
                        rs.getDouble("valor")));
            }
        }

        return sb.toString();
    }
}