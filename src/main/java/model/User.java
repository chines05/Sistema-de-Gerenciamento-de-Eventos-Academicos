package model;

public abstract class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String role; // ADMIN, ALUNO, PROFESSOR, etc.

    public abstract boolean login(String email, String password);

    public String getName() {
        return this.name;
    }

    public String setName(String name) {
        return this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public String setEmail(String email) {
        return this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public String setPassword(String password) {
        return this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public String setRole(String role) {
        return this.role = role;
    }

}