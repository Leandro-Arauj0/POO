package sistema_info;

import java.util.Optional;

public class AuthService {
    public Pessoa login(String email, String senha){
        Optional<Pessoa> op = DataStore.findUserByEmail(email);

        if(op.isPresent()){
            Pessoa p = op.get();
            if(p.getSenha().equals(senha)) 
            	return p;
        }
        return null;
    }
}
