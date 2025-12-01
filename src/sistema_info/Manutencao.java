package sistema_info;

public class Manutencao implements Servico {
    public TipoServico getTipo(){ return TipoServico.MANUTENCAO; }

    public double calcularPreco(Tecnico t){
        return t.getTarifaBase() + 50;
    }
}
