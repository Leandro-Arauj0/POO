package sistema_info;

public enum TipoServico {
    MANUTENCAO("Manutenção de computadores"),
    INSTALACAO_REDE("Instalação de redes"),
    CONSULTORIA("Consultoria");

    private final String desc;
    TipoServico(String d) { 
    	this.desc = d;  
    }
    public String toString() { 
    	return desc; 
    }
}
