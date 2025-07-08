package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Participante extends User {
    
    private String role;

    public Participante(String nome, String email, String senha, String role) {
        super(nome, email, senha);
        this.role = role;
    }
}
