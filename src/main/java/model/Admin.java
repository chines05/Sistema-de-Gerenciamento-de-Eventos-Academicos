package model;

public class Admin extends User {
    public Admin(String nome, String email, String senha) {
        super(nome, email, senha);
    }

    @Override
    public boolean login(String email, String senha) {
        return true;
    }
}