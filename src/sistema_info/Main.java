package sistema_info;

import java.util.*;
import java.time.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DataStore.initSampleData();
        AuthService auth = new AuthService();

        while (true) {
            System.out.println("=== SISTEMA DE CHAMADOS - LOGIN ===");
            System.out.println("1) Entrar");
            System.out.println("2) Cadastrar cliente");
            System.out.println("0) Sair");
            System.out.print("Escolha: ");

            try {
                int opt = Integer.parseInt(sc.nextLine());
                if (opt == 0) {
                    System.out.println("Saindo...");
                    break;
                }
                switch (opt) {
                    case 1: {
                        System.out.print("Email: ");
                        String email = sc.nextLine().trim();
                        System.out.print("Senha: ");
                        String senha = sc.nextLine().trim();
                        Pessoa user = auth.login(email, senha);
                        if (user != null) {
                            System.out.println("Bem-vindo, " + user.getNome() + "!\n");
                            if (user instanceof Cliente) {
                                clienteMenu((Cliente) user, sc);
                            } else if (user instanceof Tecnico) {
                                tecnicoMenu((Tecnico) user, sc);
                            } else {
                                adminMenu(sc);
                            }
                        } else {
                            System.out.println("Login inválido. Tente novamente.\n");
                        }
                        break;
                    }
                    case 2: {
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();
                        System.out.print("Senha: ");
                        String senha = sc.nextLine();
                        System.out.print("Endereço: ");
                        String end = sc.nextLine();
                        Cliente c = new Cliente(UUID.randomUUID().toString(), nome, email, senha, end);
                        DataStore.addCliente(c);
                        System.out.println("Cadastro concluído. Faça login.\n");
                        break;
                    }
                    default:
                        System.out.println("Opção inválida.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida — use números.\n");
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }

        sc.close();
    }

    private static void clienteMenu(Cliente cliente, Scanner sc) {
        while (true) {
            System.out.println("--- MENU CLIENTE ---");
            System.out.println("1) Abrir chamado");
            System.out.println("2) Listar meus chamados");
            System.out.println("3) Ver histórico de um chamado");
            System.out.println("0) Logout");
            System.out.print("Escolha: ");
            try {
                int opt = Integer.parseInt(sc.nextLine());
                if (opt == 0) break;
                switch (opt) {
                    case 1: abrirChamado(cliente, sc); break;
                    case 2: listarChamadosCliente(cliente); break;
                    case 3: visualizarHistoricoCliente(cliente, sc); break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) { System.out.println("Use números."); }
        }
    }

    private static void tecnicoMenu(Tecnico tecnico, Scanner sc) {
        while (true) {
            System.out.println("--- MENU TÉCNICO ---");
            System.out.println("1) Ver chamados disponíveis");
            System.out.println("2) Aceitar chamado (atribuir a mim)");
            System.out.println("3) Atualizar status / adicionar histórico");
            System.out.println("4) Listar meus chamados");
            System.out.println("0) Logout");
            System.out.print("Escolha: ");
            try {
                int opt = Integer.parseInt(sc.nextLine());
                if (opt == 0) break;
                switch (opt) {
                    case 1: listarChamadosDisponiveis(); break;
                    case 2: aceitarChamado(tecnico, sc); break;
                    case 3: atualizarChamado(tecnico, sc); break;
                    case 4: listarChamadosTecnico(tecnico); break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) { System.out.println("Use números."); }
        }
    }

    private static void adminMenu(Scanner sc) {
        while (true) {
            System.out.println("--- MENU ADMIN (visualização) ---");
            System.out.println("1) Listar técnicos");
            System.out.println("2) Listar clientes");
            System.out.println("3) Listar todos os chamados");
            System.out.println("0) Logout");
            System.out.print("Escolha: ");
            try {
                int opt = Integer.parseInt(sc.nextLine());
                if (opt == 0) break;
                switch (opt) {
                    case 1: DataStore.listTecnicos(); break;
                    case 2: DataStore.listClientes(); break;
                    case 3: DataStore.listChamados(); break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) { System.out.println("Use números."); }
        }
    }

    private static void abrirChamado(Cliente cliente, Scanner sc) {
        try {
            System.out.println("Escolha o tipo de serviço:");
            for (int i = 0; i < TipoServico.values().length; i++) {
                System.out.println((i+1) + ") " + TipoServico.values()[i]);
            }
            System.out.print("Opção: ");
            int t = Integer.parseInt(sc.nextLine()) - 1;
            TipoServico st = TipoServico.values()[t];
            System.out.print("Descrição do problema: ");
            String desc = sc.nextLine();

            Servico servico;
            switch (st) {
                case MANUTENCAO: servico = new Manutencao(); break;
                case INSTALACAO_REDE: servico = new InstalacaoRede(); break;
                case CONSULTORIA: servico = new Consultoria(); break;
                default: servico = new Manutencao();
            }

            Chamado c = new Chamado(UUID.randomUUID().toString(), cliente, servico, desc);
            DataStore.addChamado(c);
            System.out.println("Chamado aberto com ID: " + c.getId());
        } catch (Exception e) {
            System.out.println("Erro ao abrir chamado: " + e.getMessage());
        }
    }

    private static void listarChamadosCliente(Cliente c) {
        System.out.println("Meus chamados:");
        DataStore.listChamados(c);
    }

    private static void visualizarHistoricoCliente(Cliente c, Scanner sc) {
        System.out.print("Informe o ID do chamado: ");
        String id = sc.nextLine();
        Chamado ch = DataStore.findChamadoById(id);
        if (ch == null || !ch.getCliente().equals(c)) {
            System.out.println("Chamado não encontrado ou não pertence a você.");
            return;
        }
        ch.printHistorico();
    }

    private static void listarChamadosDisponiveis() {
        System.out.println("Chamados sem técnico atribuído:");
        DataStore.listChamadosDisponiveis();
    }

    private static void aceitarChamado(Tecnico tecnico, Scanner sc) {
        System.out.print("Informe o ID do chamado a aceitar: ");
        String id = sc.nextLine();
        Chamado ch = DataStore.findChamadoById(id);
        if (ch == null) { System.out.println("Chamado não encontrado."); return; }
        try {
            ch.assignTo(tecnico);
            System.out.println("Chamado atribuído a " + tecnico.getNome());
        } catch (IllegalStateException e) {
            System.out.println("Não foi possível atribuir: " + e.getMessage());
        }
    }

    private static void atualizarChamado(Tecnico tecnico, Scanner sc) {
        System.out.print("Informe o ID do chamado: ");
        String id = sc.nextLine();
        Chamado ch = DataStore.findChamadoById(id);
        if (ch == null) { System.out.println("Chamado não encontrado."); return; }
        if (!tecnico.equals(ch.getTecnico())) { System.out.println("Chamado não atribuído a você."); return; }

        System.out.println("1) Atualizar status");
        System.out.println("2) Adicionar entrada de histórico");
        System.out.print("Escolha: ");
        try {
            int op = Integer.parseInt(sc.nextLine());
            if (op == 1) {
                System.out.println("Escolha status:");
                for (int i=0;i<ChamadoStatus.values().length;i++) System.out.println((i+1)+") "+ChamadoStatus.values()[i]);
                int s = Integer.parseInt(sc.nextLine())-1;
                ch.setStatus(ChamadoStatus.values()[s]);
                System.out.println("Status atualizado.");
            } else if (op == 2) {
                System.out.print("Descrição da ação: ");
                String d = sc.nextLine();
                ch.addHistorico(new HistoricoAtendimento(LocalDateTime.now(), d, tecnico));
                System.out.println("Entrada adicionada ao histórico.");
            } else System.out.println("Opção inválida.");

            if (ch.getStatus() == ChamadoStatus.CONCLUIDO) {
                double preco = ch.calcularPreco();
                System.out.println("Chamado concluído. Preço cobrado: R$ " + String.format("%.2f", preco));
            }
        } catch (NumberFormatException e) { System.out.println("Entrada inválida."); }
    }

    private static void listarChamadosTecnico(Tecnico t) {
        System.out.println("Seus chamados:");
        DataStore.listChamados(t);
    }
}
