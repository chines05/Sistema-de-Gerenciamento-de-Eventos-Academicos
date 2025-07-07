package model;

public class Evento {
    private int id;
    private String nome;
    private String descricao;
    private String dataInicio;
    private String dataFim;
    private int vagasTotal;
    private int vagasDisponivel;

    public Evento(String nome, String descricao, String dataInicio, String dataFim, int vagasTotal, int vagasDisponivel) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.vagasTotal = vagasTotal;
        this.vagasDisponivel = vagasDisponivel;
    }

    public Evento(String nome, String descricao, String dataInicio, String dataFim, int id) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataInicio() {
        return this.dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return this.dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public int getVagasTotal() {
        return this.vagasTotal;
    }

    public void setVagasTotal(int vagasTotal) {
        this.vagasTotal = vagasTotal;
    }

    public int getVagasDisponiveis() {
        return this.vagasDisponivel;
    }

    public void setVagasDisponiveis(int vagasDisponivel) {
        this.vagasDisponivel = vagasDisponivel;
    }
}