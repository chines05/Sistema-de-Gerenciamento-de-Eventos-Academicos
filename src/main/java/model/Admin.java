package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Admin extends User {
    public Admin(String nome, String email, String senha) {
        super(nome, email, senha);
    }

}