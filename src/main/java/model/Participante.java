package model;

public class Participante extends User {
    private String role;

    public Participante(String nome, String email, String senha, String role) {
        super(nome, email, senha);
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
