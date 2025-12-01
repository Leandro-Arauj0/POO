package sistema_info;

import java.util.ArrayList;
import java.util.List;

public class Tecnico extends Pessoa {
    private Especializacao especializacao;
    private double tarifaBase;
    private List<Chamado> chamados = new ArrayList<>();

    public Tecnico(String id, String nome, String email, String senha, Especializacao esp, double tarifaBase) {
        super(id, nome, email, senha);
        this.especializacao = esp;
        this.tarifaBase = tarifaBase;
    }

    public Especializacao getEspecializacao() { return especializacao; }
    public double getTarifaBase() { return tarifaBase; }
    public void addChamado(Chamado c) { chamados.add(c); }
    public List<Chamado> getChamados() { return chamados; }
}
