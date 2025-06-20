package view;

import model.Admin;
import dao.UserDAO;
import model.Participante;
import model.User;
import utils.ConnectionFactory;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//        System.out.println("Criando admin...");
//
//        try {
//            Admin admin = new Admin("Chines Porto", "chines@email.com", "chines05");
//            UserDAO userDAO = new UserDAO();
//
//            userDAO.createUser(admin);
//            System.out.println("Admin criado com ID: " + admin.getId());
//
//        } catch (Exception e) {
//            System.err.println("Erro ao criar usuário: " + e.getMessage());
//        }


        menuLogin();


    }

    public static void criarTabelas() {
        ConnectionFactory.criarTabelas();
    }

    public static void bemVindo() {
        System.out.println("Bem-vindo ao Sistema de Gerenciamento de Eventos Acadêmicos!");
    }

    public static void menuLogin() {

        bemVindo();

        int opcao;
        do {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Fazer login");
            System.out.println("2 - Cadastrar-se");
            System.out.println("3 - Sair");
            System.out.print("Opção: ");
            opcao = new Scanner(System.in).nextInt();

            switch (opcao) {
                case 1:
                    login();
                    break;
                case 2:
                    cadastrar();
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 3);

    }

    public static void login() {

        Scanner sc = new Scanner(System.in);

        System.out.println("\nFaça seu login");
        System.out.println("Entre com suas credenciais.");

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        try {
            UserDAO userDAO = new UserDAO();
            User usuario = userDAO.login(email, senha);

            if (usuario != null) {
                System.out.println("Login bem-sucedido! Bem-vindo, " + usuario.getNome());


                if (usuario instanceof Admin) {
                    menuAdmin();
                } else if (usuario instanceof Participante) {
                    Participante participante = (Participante) usuario;
                    System.out.println("Você está logado como: " + participante.getRole());
                    // menuParticipante();
                    System.out.println("Área do participante (em desenvolvimento)");
                }
            } else {
                System.out.println("Email ou senha incorretos!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao fazer login: " + e.getMessage());
        }

    }

    public static void logout() {

        menuLogin();

    }

    public static void cadastrar() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nCadastrar-se");
        System.out.println("Informe seus dados.");

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Senha: ");
        String senha = sc.nextLine();

        int opcaoRole;
        String role = "";

        System.out.println("\nEscolha uma opção de cadastro:");

        System.out.println("1 - Aluno");
        System.out.println("2 - Professor");
        System.out.println("3 - Profissional");
        System.out.println("4 - Voltar");
        System.out.println("5 - Sair");

        System.out.print("Opção: ");

        opcaoRole = sc.nextInt();

        switch (opcaoRole) {
            case 1:
                role = "ALUNO";
                break;
            case 2:
                role = "PROFESSOR";
                break;
            case 3:
                role = "PROFISSIONAL";
                break;
            case 4:
                System.out.println("Voltando ao menu de login...");
                menuLogin();
                break;
            case 5:
                System.out.println("Saindo...");
                logout();
                break;
            default:
                System.out.println("Opção inválida!");
        }

        try {
            UserDAO userDAO = new UserDAO();
            userDAO.createUser(new Participante(nome, email, senha, role));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void menuAdmin() {
        System.out.println("Bem-vindo ao menu do admin!");

        int opcao;
        do {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Criar Eventos");
            System.out.println("2 - Editar Eventos");
            System.out.println("3 - Excluir Eventos");
            System.out.println("4 - Cadastrar Atividades");
            System.out.println("5 - Definir Limite de Vagas");
            System.out.println("6 - Confirmar Pagamentos");
            System.out.println("7 - Visualizar Inscritos");
            System.out.println("8 - Definir Taxas");
            System.out.println("9 - Relatórios Participação");
            System.out.println("10 - Relatórios Financeiros");
            System.out.println("11 - Sair");
            System.out.print("Opção: ");



            opcao = new Scanner(System.in).nextInt();

            switch (opcao) {
                case 1:
                    // Criar Eventos
                    break;
                case 2:
                    // Editar Eventos
                    break;
                case 3:
                    // Excluir Eventos
                    break;
                case 4:
                    // Cadastrar Atividades
                    break;
                case 5:
                    // Definir Limite de Vagas
                    break;
                case 6:
                    // Confirmar Pagamentos
                    break;
                case 7:
                    // Visualizar Inscritos
                    break;
                case 8:
                    // Definir Taxas
                    break;
                case 9:
                    // Relatórios Participação
                    break;
                case 10:
                    // Relatórios Financeiros
                    break;
                case 11:
                    logout();
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 11);

    }

}