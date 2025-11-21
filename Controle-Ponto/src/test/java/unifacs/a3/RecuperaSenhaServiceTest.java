package unifacs.a3;



import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RecuperaSenhaServiceTest {

    @Test
    void deveRetornarMensagemQuandoUsuarioNaoExiste() throws Exception {
        
        UsuarioRepository repo = mock(UsuarioRepository.class);
        when(repo.existePorEmail("inexistente@email.com")).thenReturn(false);

        RecuperaSenhaService service = new RecuperaSenhaService(repo);

        String resultado = service.recuperarSenha("inexistente@email.com", 1234);

        // Assert
        assertEquals("Usuário não encontrado!", resultado);
       
    }

    @Test
    void deveAtualizarSenhaQuandoUsuarioExiste() throws Exception {
       
        UsuarioRepository repo = mock(UsuarioRepository.class);
        when(repo.existePorEmail("benicio.gois@gtv.com")).thenReturn(true);

        RecuperaSenhaService service = new RecuperaSenhaService(repo);

      
        String resultado = service.recuperarSenha("benicio.gois@gtv.com", 9999);

        // Assert
        assertEquals("Senha alterada!", resultado);
    }
}
