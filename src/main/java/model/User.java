package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class User {

    private int id;
    private String nome;
    private String email;
    private String senha;

    public User(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

}