package dao;

import model.Evento;
import utils.ConnectionFactory;

import java.sql.*;

public class AtividadeDAO {

    public void criarAtividade(Evento evento) throws SQLException {
        String sql = "INSERT INTO Evento (nome, descricao, data_inicio, data_fim, vagas_total, vagas_disponivel) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, evento.getNome());
            stmt.setString(2, evento.getDescricao());
            stmt.setString(3, evento.getDataInicio());
            stmt.setString(4, evento.getDataFim());
            stmt.setInt(5, evento.getVagasTotal());
            stmt.setInt(6, evento.getVagasDisponiveis());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    evento.setId(rs.getInt(1));
                }
            }
        }
    }

}
