package sistema_info;

import java.util.*;
import java.util.UUID;

public class DataStore {

    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Tecnico> tecnicos = new ArrayList<>();
    private static List<Chamado> chamados = new ArrayList<>();

    public static void initSampleData(){
        Tecnico t1 = new Tecnico("t1","Carlos","carlos@suporte.com","123",Especializacao.HARDWARE,80);
        Tecnico t2 = new Tecnico("t2","Mariana","mariana@suporte.com","123",Especializacao.REDES,90);
        Tecnico t3 = new Tecnico("admin","Administrador","admin@sys.com","admin",Especializacao.GERAL,100);

        tecnicos.add(t1);
        tecnicos.add(t2);
        tecnicos.add(t3);

        Cliente c = new Cliente("c1","João","joao@cliente.com","abc","Rua A, 123");
        clientes.add(c);

        Chamado ch = new Chamado(UUID.randomUUID().toString(), c, new Manutencao(), "Computador não liga");
        chamados.add(ch);
    }

    public static void addCliente(Cliente c){ clientes.add(c); }
    public static void addTecnico(Tecnico t){ tecnicos.add(t); }
    public static void addChamado(Chamado c){ chamados.add(c); }

    public static Optional<Pessoa> findUserByEmail(String email){
        for(Cliente c : clientes) if(c.getEmail().equalsIgnoreCase(email)) return Optional.of(c);
        for(Tecnico t : tecnicos) if(t.getEmail().equalsIgnoreCase(email)) return Optional.of(t);
        return Optional.empty();
    }

    public static Chamado findChamadoById(String id){
        for(Chamado c : chamados) if(c.getId().equals(id)) return c;
        return null;
    }

    public static void listClientes(){
        clientes.forEach(c -> System.out.println(c.getNome() + " - " + c.getEmail()));
    }

    public static void listTecnicos(){
        tecnicos.forEach(t -> System.out.println(t.getNome() + " - " + t.getEspecializacao()));
    }

    public static void listChamados(){
        chamados.forEach(DataStore::printResumo);
    }

    public static void listChamados(Cliente c){
        chamados.stream().filter(x -> x.getCliente().equals(c)).forEach(DataStore::printResumo);
    }

    public static void listChamados(Tecnico t){
        chamados.stream().filter(x -> t.equals(x.getTecnico())).forEach(DataStore::printResumo);
    }

    public static void listChamadosDisponiveis(){
        chamados.stream().filter(c -> c.getTecnico()==null).forEach(DataStore::printResumo);
    }

    private static void printResumo(Chamado c){
        System.out.println("ID: " + c.getId() +
                " | Cliente: " + c.getCliente().getNome() +
                " | Serviço: " + c.getServico().getTipo() +
                " | Status: " + c.getStatus() +
                (c.getTecnico() != null ? " | Técnico: " + c.getTecnico().getNome() : ""));
    }
}
