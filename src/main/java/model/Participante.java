package model;

import java.util.List;

public class Participante extends User {
    //private double taxaInscricao;
    private String role;

    public Participante(String nome, String email, String senha, String role) {
        super(nome, email, senha);
        //this.taxaInscricao = taxaInscricao;
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    @Override
    public boolean login(String email, String senha) {
        return true;
    }

    @Override
    public void logout() {
        return;
    }

//    public double calcularTaxa() {
//        return taxaInscricao;
//    }
//
//    public boolean InscreverEmEvento(Evento evento) {
//        return true;
//    }
//
//    public boolean inscreverEmAtividade(Atividade atividade) {
//        return true;
//    }
//
//    public boolean efetuarPagamento(Inscricao inscricao) {
//        return true;
//    }
//
//    public List<Evento> visualizarEventosInscritos() {
//        return null;
//    }
//
//    public List<Atividade> visualizarAtividadesInscritas() {
//        return null;
//    }
//
//    public String statusPagamento() {
//        return null;
//    }
//
//    public boolean cancelarInscricao() {
//        return true;
//    }

}
