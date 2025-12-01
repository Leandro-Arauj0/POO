package sistema_info;

public class Consultoria implements Servico {
    public TipoServico getTipo(){ 
    	return TipoServico.CONSULTORIA; 
    }

    public double calcularPreco(Tecnico t){
        return t.getTarifaBase() * 2 + 100;
    }
}
