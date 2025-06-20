package dao;

import model.Admin;
import model.User;
import utils.ConnectionFactory;
import java.sql.*;

public class UserDAO {

    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO User (nome, email, senha, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());
            stmt.setString(4, user instanceof Admin ? "ADMIN" : "PARTICIPANTE");

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }
        }
    }



}