package dao;

import model.Atividade;
import model.Evento;
import utils.ConnectionFactory;

import java.sql.*;

public class AtividadeDAO {

    public void criarAtividade(Atividade atividade, int evento_id) throws SQLException {

        String sql = "INSERT INTO ATIVIDADE (evento_id, nome, descricao, data_realizacao, hora_inicio, hora_fim, limite_inscritos, tipo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, evento_id);
            stmt.setString(2, atividade.getNome());
            stmt.setString(3, atividade.getDescricao());
            stmt.setString(4, atividade.getDataRealizacao());
            stmt.setString(5, atividade.getHoraInicio());
            stmt.setString(6, atividade.getHoraFim());
            stmt.setInt(7, atividade.getLimiteInscritos());
            stmt.setString(8, atividade.getTipo());

            stmt.executeUpdate();
        }

    }

    public void busacarAtividadeId(int id) throws SQLException {

        String sql = "SELECT * FROM "

    }

}
