package model;

public class Atividade {
    private int id;
    private String nome;
    private String descricao;
    private String data_realizacao;
    private String hora_inicio;
    private String hora_fim;
    private int limite_inscritos;
    private int vagas_disponivel;
    private String tipo;

    public Atividade (String nome, String descricao, String data_realizacao, String hora_inicio, String hora_fim, int limite_inscritos, int vagas_disponivel, String tipo) {
        this.nome = nome;
        this.descricao = descricao;
        this.data_realizacao = data_realizacao;
        this.hora_inicio = hora_inicio;
        this.hora_fim = hora_fim;
        this.limite_inscritos = limite_inscritos;
        this.vagas_disponivel = vagas_disponivel;
        this.tipo = tipo;
    }

   public Atividade (int id, String nome, String descricao, String data_realizacao, String hora_inicio, String hora_fim, int limite_inscritos, int vagas_disponivel, String tipo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.data_realizacao = data_realizacao;
        this.hora_inicio = hora_inicio;
        this.hora_fim = hora_fim;
        this.limite_inscritos = limite_inscritos;
        this.vagas_disponivel = vagas_disponivel;
        this.tipo = tipo;
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

    public String getDataRealizacao() {
        return this.data_realizacao;
    }

    public void setDataRealizacao(String data_realizacao) {
        this.data_realizacao = data_realizacao;
    }

    public String getHoraInicio() {
        return this.hora_inicio;
    }

    public void setHoraInicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHoraFim() {
        return this.hora_fim;
    }

    public void setHoraFim(String hora_fim) {
        this.hora_fim = hora_fim;
    }

    public int getLimiteInscritos() {
        return this.limite_inscritos;
    }

    public void setLimiteInscritos(int limite_inscritos) {
        this.limite_inscritos = limite_inscritos;
    }

    public int getVagasDisponivel() {
        return this.vagas_disponivel;
    }

    public void setVagasDisponivel(int vagas_disponivel) {
        this.vagas_disponivel = vagas_disponivel;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}