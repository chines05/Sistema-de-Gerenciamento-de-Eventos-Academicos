package view;

import exceptions.EmailDuplicadoException;
import exceptions.EmailInvalidoException;
import exceptions.SenhaFracaException;
import model.Admin;
import dao.UserDAO;
import model.Participante;
import model.User;
import utils.ConnectionFactory;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//        criarTabelas();

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
                    menuParticipante();
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
        String nome = sc.nextLine().trim();

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Senha (mínimo 6 caracteres): ");
        String senha = sc.nextLine().trim();

        String role = selecionarRole(sc);
        if (role == null) return;

        try {
            UserDAO userDAO = new UserDAO();
            Participante novoUsuario = new Participante(nome, email, senha, role);
            userDAO.createUser(novoUsuario);
            System.out.println("Cadastro realizado com sucesso!");
        } catch (EmailDuplicadoException e) {
            System.err.println("Erro no cadastro: " + e.getMessage());
            pause(1000);
        } catch (EmailInvalidoException e) {
            System.err.println("Erro no cadastro: " + e.getMessage());
            pause(1000);
        } catch (SenhaFracaException e) {
            System.err.println("Erro no cadastro: " + e.getMessage());
            pause(1000);
        } catch (SQLException e) {
            System.err.println("Erro no banco de dados: " + e.getMessage());
            pause(1000);
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            pause(1000);
        }

    }

    private static void pause(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static String selecionarRole(Scanner sc) {
        while (true) {
            System.out.println("\nTipo de conta:");
            System.out.println("1 - Aluno");
            System.out.println("2 - Professor");
            System.out.println("3 - Profissional");
            System.out.println("4 - Voltar");
            System.out.print("Opção: ");

            try {
                int opcao = Integer.parseInt(sc.nextLine());
                switch (opcao) {
                    case 1: return "ALUNO";
                    case 2: return "PROFESSOR";
                    case 3: return "PROFISSIONAL";
                    case 4: return null;
                    default: System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números!");
            }
            pause(1000);
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

    public static void menuParticipante() {
        System.out.println("Bem-vindo ao menu do participante!");

        int opcao;
        do {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Visualizar Eventos");
            System.out.println("2 - Inscrever-se em Evento");
            System.out.println("3 - Visualizar Atividades");
            System.out.println("4 - Inscrever-se em Atividade");
            System.out.println("5 - Visualizar Informações do Evento");
            System.out.println("6 - Visualizar Informações da Atividade");
            System.out.println("7 - Visualizar Eventos Inscritos");
            System.out.println("8 - Visualizar Atividades Inscritas");
            System.out.println("9 - Visualizar Informações do Pagamento");
            System.out.println("10 - Cancelar Inscrição");
            System.out.println("11 - Sair");
            System.out.print("Opção: ");

            opcao = new Scanner(System.in).nextInt();

            switch (opcao) {
                case 1:
                    // Visualizar Eventos
                    break;
                case 2:
                    // Inscrever-se em Eventos
                    break;
                case 3:
                    // Visualizar Atividades
                    break;
                case 4:
                    // Visualizar Informações do Evento
                    break;
                case 5:
                    // Visualizar Informações da Atividade
                    break;
                case 6:
                    // Visualizar Informações do Participante
                    break;
                case 7:
                    // Visualizar Informações do Aluno
                    break;
                case 8:
                    // Visualizar Informações do Professor
                    break;
                case 9:
                    // Visualizar Informações do Profissional
                    break;
                case 10:
                    // Visualizar Informações do Pagamento
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