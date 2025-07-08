package view;

import dao.*;
import exceptions.*;
import model.*;
import utils.ConnectionFactory;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

       try {
            criarTabelas();
            menuLogin();
            //cadastrar();
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        } finally {
            sc.close();
        }

    }

    public static void criarTabelas() {

        ConnectionFactory.criarTabelas();

    }

    public static void bemVindo() {

        System.out.println("Bem-vindo ao Sistema de Gerenciamento de Eventos Acadêmicos!");

    }

    public static void menuLogin() {

        bemVindo();

        int opcao = 0;
        do {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Fazer login");
            System.out.println("2 - Cadastrar-se");
            System.out.println("3 - Sair");
            System.out.print("Opção: ");

            try {
                String linha = sc.nextLine();

                opcao = Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número válido.");
                continue;
            }

            switch (opcao) {
                case 1:
                    login();
                    break;
                case 2:
                    cadastrar();
                    break;
                case 3:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 3);

    }

    public static void login() {

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

                    return;
                } else if (usuario instanceof Participante) {
                    Participante participante = (Participante) usuario;
                    System.out.println("Você está logado como: " + participante.getRole());
                    menuParticipante(participante);

                    return;
                }
            } else {
                System.out.println("Email ou senha incorretos!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao fazer login: " + e.getMessage());
        }

    }

    public static void logout() {

        System.out.println("Deslogado com sucesso!");

    }

    public static void cadastrar() {

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

    public static void pause(int millis) {

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    public static String selecionarRole(Scanner sc) {

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

        System.out.println("--- Menu do Admin ---");

        int opcao;
        do {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Menu de Evento");
            System.out.println("2 - Menu de Atividades");
            System.out.println("3 - Menu valores Inscricoes");
            System.out.println("4 - Sair");

            System.out.print("Opção: ");

            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    menuEvento();
                    break;
                case 2:
                    menuAtividades();
                    break;
                case 3:
                    menuValoresInscricoes();
                    break;
                case 4:
                    logout();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (true);

    }

    public static void menuEvento() {

        System.out.println("\n--- Menu do Evento ---");

        int opcao;
        do {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Visualizar Eventos");
            System.out.println("2 - Criar Evento");
            System.out.println("3 - Editar Evento");
            System.out.println("4 - Excluir Evento");
            System.out.println("5 - Menu confirmar/recusar inscricoes");
            System.out.println("6 - Sair");

            System.out.print("Opção: ");

            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    visualizarEventos();
                    break;
                case 2:
                    criarEvento();
                    break;
                case 3:
                    editarEvento();
                    break;
                case 4:
                    deletarEvento();
                    break;
                case 5:
                    menuConfirmarInscricoes();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (true);

    }

    public static void visualizarEventos() {

        try {
            String listaFormatada = new EventoDAO().listarEventosFormatados();
            System.out.println(listaFormatada);
        } catch (SQLException e) {
            System.err.println("Erro ao listar eventos: " + e.getMessage());
        }

    }

    public static void criarEvento() {

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

    public static void editarEvento() {

        System.out.println("\n--- Editar Evento ---");
        System.out.println("Digite as informações do evento:");

        sc.nextLine();

        System.out.print("ID do Evento que deseja editar: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Qual o novo nome do evento: ");
        String nome = sc.nextLine();

        System.out.print("Descrição: ");
        String descricao = sc.nextLine();

        System.out.print("Data Início (YYYY-MM-DD): ");
        String dataInicio = sc.nextLine();

        System.out.print("Data Fim (YYYY-MM-DD): ");
        String dataFim = sc.nextLine();

        try {
            Evento evento = new Evento(nome, descricao, dataInicio, dataFim, id);
            new EventoDAO().editarEvento(evento);
            System.out.println("Evento editado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao editar evento: " + e.getMessage());
        }

    }

    public static void deletarEvento() {

        System.out.println("\n--- Deletar Evento ---");

        sc.nextLine();

        System.out.println("Digite o ID do evento que deseja deletar: ");
        int id = sc.nextInt();

        do {
            System.out.println("Tem certeza que deseja deletar o evento? (S/N)");
            String opcao = sc.next();

            if (opcao.equalsIgnoreCase("S")) {
                break;
            }

            if (opcao.equalsIgnoreCase("N")) {
                System.out.println("Operação cancelada!");
                return;
            }

        } while (true);

        sc.nextLine();

        try {
            new EventoDAO().deletarEvento(id);
            System.out.println("Evento deletado com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao deletar evento: " + e.getMessage());
        }

    }

    public static void menuConfirmarInscricoes() {

        InscricaoEventoDAO inscricaoDAO = new InscricaoEventoDAO();

        try {
            String inscricoes = inscricaoDAO.listarInscricoesPendentes();
            System.out.println(inscricoes);

            if (inscricoes.equals("\nNão há inscrições pendentes de confirmação.")) {
                return;
            }

            System.out.print("\nDigite o ID da inscrição para gerenciar ou 0 para voltar: ");
            int id = sc.nextInt();
            sc.nextLine();

            if (id > 0) {
                System.out.println("\nEscolha a ação:");
                System.out.println("1 - Confirmar inscrição");
                System.out.println("2 - Recusar inscrição");
                System.out.println("3 - Manter pendente");
                System.out.print("Opção: ");

                int opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1:
                        inscricaoDAO.atualizarStatusInscricao(id, "CONFIRMADO");
                        System.out.println("Inscrição confirmada com sucesso!");
                        break;
                    case 2:
                        inscricaoDAO.atualizarStatusInscricao(id, "RECUSADO");
                        System.out.println("Inscrição recusada com sucesso!");
                        break;
                    case 3:
                        System.out.println("Status mantido como pendente.");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao processar inscrições: " + e.getMessage());
        }

    }

    public static void menuValoresInscricoes() {

        System.out.println("\n--- Menu Valores Inscricoes ---");

        int opcao;

        do {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Visualizar Valores");
            System.out.println("2 - Editar Valores");
            System.out.println("3 - Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    visualizarValoresInscricoes();
                    break;
                case 2:
                    editarValoresInscricoes();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 3);

    }

    public static void visualizarValoresInscricoes() {

        try {
            String valores = new ConfigInscricaoDAO().listarValoresFormatado();
            System.out.println(valores);
        } catch (SQLException e) {
            System.err.println("Erro ao listar valores: " + e.getMessage());
        }

    }

    public static void editarValoresInscricoes() {

        System.out.println("\n--- Editar Valores ---");

        System.out.print("Digite o role do participante: ");
        String role = sc.nextLine();

        System.out.print("Digite o novo valor: ");
        String novoValor = sc.nextLine();

        sc.nextLine();

        try {
            new ConfigInscricaoDAO().atualizarValorInscricao(role, novoValor);
            System.out.println("Valores editados com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao editar valores: " + e.getMessage());
        } catch (ValorInvalidoException e) {
            System.err.println("Erro ao editar valores: " + e.getMessage());
        }

    }

    public static void menuAtividades() {

        System.out.println("\n--- Menu de Atividades ---");

        int opcao;

        do {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - Criar Atividade");
            System.out.println("2 - Listar Atividades");
            System.out.println("3 - Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    criarAtividade();
                    break;
                case 2:
                    listarAtividades();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 3);
    }

    public static void criarAtividade() {

        visualizarEventos();
        
        System.out.println("Digite o ID do evento aonde deseja criar a atividade: ");
        int eventoID = sc.nextInt();
        sc.nextLine();
        
        System.out.println("\n--- Criar Atividade ---");

        System.out.print("Digite o nome da atividade: ");
        String nome = sc.nextLine();

        System.out.print("Digite a descrição da atividade: ");
        String descricao = sc.nextLine();

        System.out.print("Digite a data de realização da atividade: ");
        String dataRealizacao = sc.nextLine();

        System.out.print("Digite a hora de inicio da atividade: ");
        String horaInicio = sc.nextLine();

        System.out.print("Digite a hora de fim da atividade: ");
        String horaFim = sc.nextLine();

        System.out.print("Digite o limite de inscritos da atividade: ");
        int limiteInscritos = sc.nextInt();
        sc.nextLine();

        String tipo = tipoAtividade();

        try {
            Atividade atividade = new Atividade(nome, descricao, dataRealizacao, horaInicio, horaFim, limiteInscritos, limiteInscritos, tipo);
            new AtividadeDAO().criarAtividade(atividade, eventoID);
            System.out.println("Atividade criada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao criar atividade: " + e.getMessage());
        }

    }

    public static String tipoAtividade() {

        System.out.print("Qual o tipo da atividade: ");
        
        int opcao;
        String tipo = "";

        do {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1 - PALESTRA");
            System.out.println("2 - SIMPOSIO");
            System.out.println("3 - CURSO");
            System.out.println("4 - Sair");
            System.out.print("Opção: ");
            opcao = sc.nextInt();

            switch (opcao) {
                case 1:
                    return tipo = "PALESTRA";
                case 2:
                    return tipo = "SIMPOSIO";
                case 3:
                    return tipo = "CURSO";
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 4);

        return tipo;
    }

    public static void listarAtividades() {

        visualizarEventos();

        System.out.println("Digite o ID do evento aonde deseja listar as atividades: ");
        int eventoID = sc.nextInt();
        sc.nextLine();

        try {
            String atividades = new AtividadeDAO().listarAtividadesFormatadas(eventoID);
            System.out.println(atividades);
        } catch (SQLException e) {
            System.err.println("Erro ao listar atividades: " + e.getMessage());
        }

    }

    public static void cancelarInscricaoEvento(int usuarioId) {
        System.out.println("\n--- Cancelar Inscrição em Evento ---");
        InscricaoEventoDAO inscricaoDAO = new InscricaoEventoDAO();

        try {
            String minhasInscricoes = inscricaoDAO.listarTodasInscricoesDoUsuario(usuarioId);
            System.out.println(minhasInscricoes);

            if (minhasInscricoes.contains("Você não possui nenhuma inscrição")) {
                return;
            }

            System.out.print("\nDigite o ID da inscrição que deseja cancelar (ou 0 para voltar): ");
            int inscricaoId = sc.nextInt();
            sc.nextLine();

            if (inscricaoId == 0) {
                return;
            }
            System.out.print("Tem certeza que deseja cancelar a inscrição de ID " + inscricaoId + "? (S/N): ");
            String confirmacao = sc.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {

                inscricaoDAO.cancelarInscricao(inscricaoId);
                System.out.println("Inscrição cancelada com sucesso!");
            } else {
                System.out.println("Operação cancelada.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao processar o cancelamento: " + e.getMessage());
        }
    }

    public static void menuParticipante(Participante participante) {

        System.out.println("--- Menu do Participante ---");

        int opcao;
        do {
            System.out.println("\nEscolha uma opção:");
            System.out.println("-- Evento --");
            System.out.println("1 - Visualizar todos os eventos");
            System.out.println("2 - Se inscrever em um evento");
            System.out.println("3 - Visualizar eventos inscritos");

            System.out.println("-- Atividade --");
            System.out.println("4 - Visualizar atividades do evento");
            System.out.println("5 - Inscrever-se em Atividade");
            System.out.println("6 - Visualizar atividades inscritas");

            System.out.println("-- Pagamento --");
            System.out.println("7 - Visualizar Informações do Pagamento");
            System.out.println("8 - Cancelar Inscrição em Evento");
            System.out.println("9 - Sair");
            System.out.print("Opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    visualizarEventos();
                    break;
                case 2:
                    inscreverEmEvento(participante.getId(), participante.getRole());
                    break;
                case 3:
                    visualizarEventosInscritos(participante.getId());
                    break;
                case 4:
                    visualizarAtividadesDoEvento();
                    break;
                case 5:
                    inscreverEmAtividade(participante.getId());
                    break;
                case 6:
                    visualizarAtividadeInscritas(participante.getId());
                    break;
                case 7:
                    visualizarInformacoesPagamento(participante.getId());
                    break;
                case 8:
                    cancelarInscricaoEvento(participante.getId());
                    break;
                case 9:
                    logout();
                    return;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (true);

    }

    public static void valorInscricaoUser(String role) {

        try {
            String valores = String.valueOf(new ConfigInscricaoDAO().getValorInscricao(role));
            System.out.println("Valor da inscrição: " + valores);
        } catch (SQLException e) {
            System.err.println("Erro ao listar valores: " + e.getMessage());
        }

    }

    public static void inscreverEmEvento(int userID, String role) {
        
        System.out.println("\n--- Inscrever-se em Evento ---");
        visualizarEventos();
        
        valorInscricaoUser(role);
        
        System.out.print("\nDigite o ID do evento que deseja se inscrever: ");
        int eventoID = sc.nextInt();
        
        try {
            InscricaoEventoDAO inscricaoDAO = new InscricaoEventoDAO();
            inscricaoDAO.inscreverUsuario(userID, eventoID);
            System.out.println("Inscrição realizada com sucesso! Aguarde confirmação.");
        } catch (InscricaoPendenteException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (UsuarioJaInscritoException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (VagasEsgotadasException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (InscricaoNaoPermitidaException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro no banco de dados: " + e.getMessage());
        }

    }

    public static void visualizarEventosInscritos(int userID) {

        try {
            String eventosInscritos = new InscricaoEventoDAO().listarEventosConfirmadosDoUsuario(userID);
            System.out.println(eventosInscritos);
        } catch (SQLException e) {
            System.err.println("Erro ao listar eventos inscritos: " + e.getMessage());
        }

    }

    public static void visualizarAtividadesDoEvento() {

        visualizarEventos();

        System.out.println("Digite o ID do evento aonde deseja listar as atividades: ");
        int eventoID = sc.nextInt();
        sc.nextLine();

        try {
            String atividades = new AtividadeDAO().listarAtividadesFormatadas(eventoID);
            System.out.println(atividades);
        } catch (SQLException e) {
            System.err.println("Erro ao listar atividades: " + e.getMessage());
        }

    }

    public static void inscreverEmAtividade(int userID) {

        System.out.println("\n--- Inscrever-se em Atividade ---");
        visualizarAtividadesDoEvento();

        System.out.print("\nDigite o ID da atividade que deseja se inscrever: ");
        int atividadeID = sc.nextInt();

        try {
            InscricaoAtividadeDAO inscricaoAtividadeDAO = new InscricaoAtividadeDAO();
            inscricaoAtividadeDAO.inscreverUsuario(userID, atividadeID);
            System.out.println("Inscrição realizada com sucesso! Aguarde confirmação.");
        } catch (VagasEsgotadasException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (UsuarioJaInscritoException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (InscricaoNaoPermitidaException e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (InscricaoPendenteException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Erro no banco de dados: " + e.getMessage());
        }

    }

    public static void visualizarAtividadeInscritas(int userID) {

        try {
            String atividadesInscritas = new InscricaoAtividadeDAO().listarAtividadesInscritas(userID);
            System.out.println(atividadesInscritas);
        } catch (SQLException e) {
            System.err.println("Erro ao listar atividades inscritas: " + e.getMessage());
        }
        
    }

    public static void visualizarInformacoesPagamento(int userID) {
        try {
            String informacoesPagamento = new InscricaoEventoDAO().listarTodasInscricoesDoUsuario(userID);
            System.out.println(informacoesPagamento);
        } catch (SQLException e) {
            System.err.println("Erro ao listar informações de pagamento: " + e.getMessage());
        }
    }
}