package unifacs.a3;

import java.sql.Connection;
import java.sql.SQLException;

public class RecuperaSenhaService {

    private final UsuarioRepository usuarioRepository;

    public RecuperaSenhaService(UsuarioRepository usuarioRepository)throws SQLException  {
        Connection con = UsuarioBD.conexao_BD(); 
        this.usuarioRepository = new UsuarioRepository(con);
        
    }

    public String recuperarSenha(String email, int novaSenha) throws Exception {
        if (!usuarioRepository.existePorEmail(email)) {
            return "Usuário não encontrado!";
        }

        usuarioRepository.atualizarSenha(email, novaSenha);
        return "Senha alterada!";
    }

}
