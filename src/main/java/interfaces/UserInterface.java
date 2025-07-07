package interfaces;

import java.sql.SQLException;

import exceptions.EmailDuplicadoException;
import exceptions.EmailInvalidoException;
import exceptions.SenhaFracaException;
import model.Participante;
import model.User;

public interface UserInterface {
    
    public void createUser(Participante user) throws SQLException, EmailDuplicadoException, SenhaFracaException, EmailInvalidoException;
    
    public User login(String email, String senha) throws SQLException;

}
