package dao;

import exceptions.EmailDuplicadoException;
import exceptions.EmailInvalidoException;
import exceptions.SenhaFracaException;
import interfaces.UserInterface;
import model.Admin;
import model.Participante;
import model.User;
import utils.ConnectionFactory;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO implements UserInterface {

    @Override
    public void createUser(Participante user) throws SQLException, EmailDuplicadoException, SenhaFracaException, EmailInvalidoException {

        if (!user.getEmail().matches("^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$")) {
            throw new EmailInvalidoException("Formato de email inválido! Ex: chines@email.com");
        }

        if (emailJaExistente(user.getEmail())) {
            throw new EmailDuplicadoException("O email " + user.getEmail() + " já está cadastrado!");
        }

        if (!user.getSenha().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$")) {
            throw new SenhaFracaException("""
            A senha deve conter:
            - Mínimo 6 caracteres
            - Pelo menos 1 letra maiúscula
            - Pelo menos 1 letra minúscula
            - Pelo menos 1 número""");
        }

        String sql = "INSERT INTO User (nome, email, senha, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            String senhaHash = BCrypt.hashpw(user.getSenha(), BCrypt.gensalt());

            stmt.setString(1, user.getNome());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, senhaHash);
            stmt.setString(4, user.getRole());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public User login(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM User WHERE email = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String senhaHash = rs.getString("senha");

                    if (BCrypt.checkpw(senha, senhaHash)) {
                        String role = rs.getString("role");

                        if ("ADMIN".equals(role)) {
                            Admin admin = new Admin(
                                    rs.getString("nome"),
                                    rs.getString("email"),
                                    senhaHash
                            );
                            admin.setId(rs.getInt("id"));
                            return admin;
                        } else {
                            Participante participante = new Participante(
                                    rs.getString("nome"),
                                    rs.getString("email"),
                                    senhaHash,
                                    role
                            );
                            participante.setId(rs.getInt("id"));
                            return participante;
                        }
                    }
                }
            }
        }
        return null;
    }

    private boolean emailJaExistente(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM User WHERE email = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

}