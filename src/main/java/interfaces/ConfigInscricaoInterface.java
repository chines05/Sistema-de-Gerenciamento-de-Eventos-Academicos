package interfaces;

import java.sql.SQLException;

import exceptions.ValorInvalidoException;

public interface ConfigInscricaoInterface {
    
    public double getValorInscricao(String role) throws SQLException;
    
    public void atualizarValorInscricao(String role, String valorInput) throws SQLException, ValorInvalidoException; 

    public String listarValoresFormatado() throws SQLException;

    
}
