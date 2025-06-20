package dao;

import model.Admin;
import model.Participante;
import model.User;
import utils.ConnectionFactory;
import java.sql.*;

public class UserDAO {

    public void createUser(Participante user) throws SQLException {
        String sql = "INSERT INTO User (nome, email, senha, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());
            stmt.setString(4, user.getRole());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }
        }
    }

    public Participante login(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM User WHERE email = ? AND senha = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Participante participante = new Participante(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getString("role")
                    );
                    participante.setId(rs.getInt("id"));
                    return participante;
                }
            }
        }
        return null;
    }

}