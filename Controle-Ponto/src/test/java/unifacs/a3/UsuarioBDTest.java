package unifacs.a3;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class UsuarioBDTest {


    
  //Teste simples de verificação de usuário
    @Test
    void deveRetornarUsuario() {
        

        Usuario usuario = CadastraEVerifica.verificaUser("TesteUser", 1234);

        assertNotNull(usuario, "O método deveria retornar um usuário válido.");
        assertEquals(1, usuario.getId(), "O ID retornado deveria ser 1.");
        assertEquals("TesteUser", usuario.getNome(), "O nome do usuário não confere.");
        assertEquals(1234, usuario.getSenha(), "A senha retornada não confere.");
        assertEquals("teste@email.com", usuario.getEmail(), "O email retornado não confere.");
    }

    // Teste simples de cadastro de usuário
      @Test
    void testSimulaCadastroUsuario() {
        String nome = "Maria";
        int senha = 1234;

        boolean resultado = CadastraEVerifica.simulaCadastro(nome, senha);

        assertTrue(resultado, "O método deveria retornar true para um cadastro válido.");
    }



}
