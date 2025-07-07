package interefaces;
import java.sql.SQLException;
import java.util.List;

import model.Evento;

public interface EventoInterface {
    
    public void criarEvento(Evento evento) throws SQLException;

    public Evento buscarPorId(int id) throws SQLException;

    public List<Evento> listarTodos() throws SQLException;

    public String listarEventosFormatados() throws SQLException;

    public void editarEvento(Evento evento) throws SQLException;

    public void deletarEvento(int id) throws SQLException;

    public boolean temVagasDisponiveis(int eventoId) throws SQLException;

}
