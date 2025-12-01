package sistema_info;

import java.util.Objects;

public abstract class Pessoa {
    protected String id;
    protected String nome;
    protected String email;
    protected String senha;

    public Pessoa(String id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getId() { 
    	return id; 
    }
    
    public String getNome() { 
    	return nome; 
    }
    
    public String getEmail() { 
    	return email; 
    }
    
    public String getSenha() { 
    	return senha; 
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pessoa)) 
        	return false;
        Pessoa p = (Pessoa) o;
        return Objects.equals(id, p.id);
    }
}

