package unifacs.a3;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;



public class UsuarioBDTest {


    
  //Teste simples de verificação de usuário
    @Test
    void deveRetornarUsuario() throws Exception  {

      // Mock do UsuarioBD
        UsuarioBD usuarioBDMock = mock(UsuarioBD.class);

        Usuario esperadoUsuario = new Usuario();
        
        esperadoUsuario.setNome("TesteUser");
        esperadoUsuario.setSenha(1234);

          // Configurando mock
        when(usuarioBDMock.verificaUser(esperadoUsuario)).thenReturn(esperadoUsuario);


        Usuario usuario = CadastraEVerifica.verificaUser(esperadoUsuario, usuarioBDMock);
       

        assertNotNull(usuario, "O método deveria retornar um usuário válido.");
       
        assertEquals("TesteUser", usuario.getNome(), "O nome do usuário não confere.");
        assertEquals(1234, usuario.getSenha(), "A senha retornada não confere.");
        
    }

    // Teste simples de cadastro de usuário
      @Test
    void testSimulaCadastroUsuario() throws Exception {
       UsuarioBD usuarioBDMock = mock(UsuarioBD.class);
       

      Usuario novoUsuario = new Usuario();
      novoUsuario.setNome("Maria");
      novoUsuario.setSenha(1234);
      novoUsuario.setEmail("luluzinho@gmail.com");  
      
          // Configurando mock
        when(usuarioBDMock.cadastra_Usuario(novoUsuario)).thenReturn(true);

      
      boolean resultado = CadastraEVerifica.simulaCadastro(novoUsuario);

      assertTrue(resultado, "O método deveria retornar true para um cadastro válido.");

    }



}
