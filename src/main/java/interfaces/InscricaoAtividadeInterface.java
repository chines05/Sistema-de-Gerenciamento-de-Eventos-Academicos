package interfaces;

import java.sql.SQLException;

import exceptions.InscricaoNaoPermitidaException;
import exceptions.InscricaoPendenteException;
import exceptions.UsuarioJaInscritoException;
import exceptions.VagasEsgotadasException;

public interface InscricaoAtividadeInterface {
    public void inscreverUsuario(int userID, int AtividadeID)
            throws SQLException, VagasEsgotadasException, UsuarioJaInscritoException, InscricaoPendenteException, InscricaoNaoPermitidaException;

    public String listarAtividadesInscritas(int userID) throws SQLException;
}
