package view;

import dao.EventoDAO;
import exceptions.EmailDuplicadoException;
import exceptions.EmailInvalidoException;
import exceptions.SenhaFracaException;
import model.Admin;
import dao.UserDAO;
import model.Evento;
import model.Participante;
import model.User;
import utils.ConnectionFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//        criarTabelas();
        visualizarEventos();

        //menuLogin();

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
                System.out.println("\nLogin bem-sucedido! Bem-vindo, " + usuario.getNome());


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
        Scanner sc = new Scanner(System.in);

        System.out.println("Bem-vindo ao menu do admin!");

        int opcao;
        do {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Visualizar Eventos");
            System.out.println("2 - Criar Evento");
            System.out.println("3 - Editar Evento");
            System.out.println("4 - Excluir Evento");
            System.out.println("5 - Cadastrar Atividade");
            System.out.println("6 - Definir Limite de Vagas");
            System.out.println("7 - Confirmar Pagamentos");
            System.out.println("8 - Visualizar Inscritos");
            System.out.println("9 - Definir Taxas");
            System.out.println("10 - Relatórios Participação");
            System.out.println("11 - Relatórios Financeiros");
            System.out.println("12 - Sair");

            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    visualizarEventos();
                    break;
                case 2:
                    criarEvento(sc);
                    break;

                case 11:
                    logout();
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 11);

    }

    public static void criarEvento(Scanner sc) {

        System.out.println("\n--- Criar Evento ---");
        System.out.println("Digite as informações do evento:");

        sc.nextLine();

        System.out.print("Nome do Evento: ");
        String nome = sc.nextLine();

        System.out.print("Descrição: ");
        String descricao = sc.nextLine();

        System.out.print("Data Início (YYYY-MM-DD): ");
        String dataInicio = sc.nextLine();

        System.out.print("Data Fim (YYYY-MM-DD): ");
        String dataFim = sc.nextLine();

        System.out.print("Total de Vagas: ");
        int vagas = sc.nextInt();

        try {
            Evento novoEvento = new Evento(nome, descricao, dataInicio, dataFim, vagas, vagas);
            new EventoDAO().criarEvento(novoEvento);
            System.out.println("Evento criado com sucesso! ID: " + novoEvento.getId());
        } catch (SQLException e) {
            System.err.println("Erro ao criar evento: " + e.getMessage());
        }
    }

    public static void visualizarEventos() {
        try {
            String listaFormatada = new EventoDAO().listarEventosFormatados();
            System.out.println(listaFormatada);
        } catch (SQLException e) {
            System.err.println("Erro ao listar eventos: " + e.getMessage());
        }

        menuAdmin();
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