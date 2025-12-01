package sistema_info;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Chamado {
    private String id;
    private Cliente cliente;
    private Tecnico tecnico; // Começa com null
    private Servico servico;
    private String descricao;
    private ChamadoStatus status = ChamadoStatus.ABERTO;
    private List<HistoricoAtendimento> historico = new ArrayList<>();
    private LocalDateTime criadoEm = LocalDateTime.now();

    public Chamado(String id, Cliente cliente, Servico servico, String descricao) {
        this.id = id;
        this.cliente = cliente;
        this.servico = servico;
        this.descricao = descricao;
        cliente.addChamado(this);
    }

    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Tecnico getTecnico() {
        return tecnico;
    }

    public Servico getServico() {
        return servico;
    }

    public String getDescricao() {
        return descricao;
    }

    public ChamadoStatus getStatus() {
        return status;
    }

    public void setStatus(ChamadoStatus status) {
        this.status = status;
    }

    public void assignTo(Tecnico tecnico) {
        if (this.tecnico != null) {
            throw new IllegalStateException("Este chamado já está atribuído a um técnico.");
        }
        this.tecnico = tecnico;
        this.status = ChamadoStatus.ATRIBUIDO;
        tecnico.addChamado(this);
    }

    public void addHistorico(HistoricoAtendimento h) {
        historico.add(h);
    }

    public double calcularPreco() {
        if (tecnico == null) {
            // fallback caso ocorra erro (não deveria acontecer)
            Tecnico padrao = new Tecnico("0", "Default", "default@x.com", "123",
                    Especializacao.GERAL, 50);
            return servico.calcularPreco(padrao);
        }
        return servico.calcularPreco(tecnico);
    }

    public void printHistorico() {
        System.out.println("\n===== HISTÓRICO DO CHAMADO " + id + " =====");
        System.out.println("Criado em: " + criadoEm);
        System.out.println("Descrição: " + descricao);
        System.out.println("Status atual: " + status);

        if (tecnico != null) {
            System.out.println("Técnico: " + tecnico.getNome() + " (" + tecnico.getEspecializacao() + ")");
        } else {
            System.out.println("Técnico: Não atribuído");
        }

        if (historico.isEmpty()) {
            System.out.println("Nenhuma entrada de histórico.");
        } else {
            for (HistoricoAtendimento h : historico) {
                System.out.println(h);
            }
        }

        System.out.println("===========================================\n");
    }
}
