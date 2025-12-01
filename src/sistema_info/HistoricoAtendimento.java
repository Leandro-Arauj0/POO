package sistema_info;

import java.time.LocalDateTime;

public class HistoricoAtendimento {
    private LocalDateTime momento;
    private String descricao;
    private Tecnico responsavel;

    public HistoricoAtendimento(LocalDateTime momento, String descricao, Tecnico responsavel){
        this.momento=momento;
        this.descricao=descricao;
        this.responsavel=responsavel;
    }

    public String toString(){
        return "[" + momento + "] " + responsavel.getNome() + ": " + descricao;
    }
}
