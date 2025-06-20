package model;

import java.util.List;

public class Admin extends User {
    public Admin(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    @Override
    public boolean login(String email, String senha) {
        return true;
    }

    @Override
    public void logout() {
        System.out.println("Logout do admin efetuado com sucesso!");
        return;
    }

//    public boolean criarEvento() {
//        return true;
//    }
//
//    public boolean editarEvento(Evento evento) {
//        return true;
//    }
//
//    public boolean excluirEvento(Integer eventoID) {
//        return true;
//    }
//
//    public boolean cadastrarAtividade() {
//        return true;
//    }
//
//    public void definirTaxaPadrao(TipoParticipante tipoParticipante, Double taxa) {
//        return;
//    }
//
//    public boolean confirmarPagamento(Inscricao inscricao) {
//        return true;
//    }
//
//    public List<Evento> visualizarEventos() {
//        return null;
//    }
//
//    public List<Participante> visualizarInscritos(Evento evento) {
//        return null;
//    }
//
//    public String visualizarStatusPagamentoUsuario(Participante participante) {
//        return null;
//    }
//
//    public boolean gerenciarValorInscricaoPorParticipante(Participante participante, Double taxa) {
//        return true;
//    }

}