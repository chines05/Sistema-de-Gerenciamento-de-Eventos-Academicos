package interfaces;

import java.sql.SQLException;

import exceptions.InscricaoNaoPermitidaException;
import exceptions.InscricaoPendenteException;
import exceptions.UsuarioJaInscritoException;
import exceptions.VagasEsgotadasException;

public interface InscricaoEventoInterface {

    public void inscreverUsuario(int usuarioId, int eventoId) 
        throws SQLException, VagasEsgotadasException, UsuarioJaInscritoException, InscricaoPendenteException, InscricaoNaoPermitidaException;

    public void atualizarStatusInscricao(int inscricaoId, String status) throws SQLException;

    public String listarInscricoesPendentes() throws SQLException;

    public boolean usuarioTemInscricaoPendente(int usuarioId, int eventoId) throws SQLException;

    public boolean usuarioJaRecusado(int usuarioId, int eventoId) throws SQLException;

    public boolean usuarioJaPedente(int usuarioId, int eventoId) throws SQLException;

    public void cancelarInscricao(int usuarioId, int eventoId) throws SQLException;

    public String listarEventosConfirmadosDoUsuario(int usuarioId) throws SQLException;
}
