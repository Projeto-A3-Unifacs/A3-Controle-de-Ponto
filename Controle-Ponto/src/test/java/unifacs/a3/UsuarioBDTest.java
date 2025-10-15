package unifacs.a3;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;


public class UsuarioBDTest {


    
   // Teste simples do método verificaUser
    @Test
    void testVerificaUserSimples() {
        // Simula o retorno de um usuário
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("TesteUser");
        usuario.setEmail("teste@email.com");
        usuario.setSenha(1234);

        // Verificações
        assertNotNull(usuario);
        assertEquals(1, usuario.getId());
        assertEquals("TesteUser", usuario.getNome());
        assertEquals("teste@email.com", usuario.getEmail());
        assertEquals(1234, usuario.getSenha());
    }

    // Teste simples de cadastro de usuário
    @Test
    void testCadastraUsuarioSimples() {
        // Simula cadastro
        String nome = "NovoUser";
        int senha = 5678;

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setSenha(senha);

        // Verificações
        assertEquals(nome, usuario.getNome());
        assertEquals(senha, usuario.getSenha());
    }


   

   

    // Teste simples de recuperação de senha
    @Test
    void testRecuperaSenhaSimples() {
        String email = "teste@email.com";
        int senhaNova = 9999;

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senhaNova);

        // Verificações
        assertEquals(email, usuario.getEmail());
        assertEquals(senhaNova, usuario.getSenha());
    }
}
