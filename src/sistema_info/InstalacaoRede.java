package sistema_info;

public class InstalacaoRede implements Servico {
    public TipoServico getTipo(){ 
    	return TipoServico.INSTALACAO_REDE; 
    }

    public double calcularPreco(Tecnico t){
        double mult = (t.getEspecializacao() == Especializacao.REDES) ? 1.0 : 1.2;
        return t.getTarifaBase() * mult + 80;
    }
}
