package sistema_info;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {
    private String endereco;
    private List<Chamado> chamados = new ArrayList<>();

    public Cliente(String id, String nome, String email, String senha, String endereco) {
        super(id, nome, email, senha);
        this.endereco = endereco;
    }

    public String getEndereco() { return endereco; }
    public void addChamado(Chamado c) { chamados.add(c); }
    public List<Chamado> getChamados() { return chamados; }
}

