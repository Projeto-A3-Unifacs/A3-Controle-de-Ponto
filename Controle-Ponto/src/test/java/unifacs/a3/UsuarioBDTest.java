package unifacs.a3;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
// Importante: para métodos void ou verificação, as vezes é bom usar verify, 
// mas para o seu caso o return true basta.
import static org.mockito.ArgumentMatchers.any; 

public class UsuarioBDTest {

    @Test
    void deveRetornarUsuario() throws Exception  {
        // Mock do UsuarioBD
        UsuarioBD usuarioBDMock = mock(UsuarioBD.class);
        Usuario esperadoUsuario = new Usuario();
        esperadoUsuario.setNome("TesteUser");
        esperadoUsuario.setSenha(1234);

        // Configurando comportamento do mock
        // Quando chamar verificaUser com ESSE objeto, retorne ESSE objeto
        when(usuarioBDMock.verificaUser(esperadoUsuario)).thenReturn(esperadoUsuario);

        // AQUI ESTAVA O PROBLEMA: Agora a classe vai usar o usuarioBDMock que passamos
        Usuario usuario = CadastraEVerifica.verificaUser(esperadoUsuario, usuarioBDMock);
       
        assertNotNull(usuario, "O método deveria retornar um usuário válido (Mock).");
        assertEquals("TesteUser", usuario.getNome());
    }

    @Test
    void testSimulaCadastroUsuario() throws Exception {
        UsuarioBD usuarioBDMock = mock(UsuarioBD.class);
       
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome("Maria");
        novoUsuario.setSenha(1234);
        novoUsuario.setEmail("luluzinho@gmail.com");  
      
        // Configurando mock para retornar true quando cadastrar
        when(usuarioBDMock.cadastra_Usuario(novoUsuario)).thenReturn(true);
      
        // CORREÇÃO: Passamos o mock para o método simulaCadastro
        boolean resultado = CadastraEVerifica.simulaCadastro(novoUsuario, usuarioBDMock);

        assertTrue(resultado, "O método deveria retornar true usando o Mock.");
    }
}
