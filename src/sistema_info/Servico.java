package sistema_info;

public interface Servico {
    TipoServico getTipo();
    double calcularPreco(Tecnico t);
}
