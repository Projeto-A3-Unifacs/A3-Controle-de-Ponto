package unifacs.a3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JustificativaAatrasoTest {

    @Test
    void deveRegistrarJustificativaDeAtestado() throws Exception {
        
        JustificativaBD repoMock = mock(JustificativaBD.class);
        JustificativaAtraso service = new JustificativaAtraso(repoMock);

        String resultado = service.registrarJustificativa(1, 50, 2);

        
        // Verificamos se o repoMock recebeu o ID do atraso (50) corretamente
        verify(repoMock).salvarJustificativa(1, 50, "Atestado");
        
       
        assertEquals("Justificativa registrada: Atestado", resultado);
    }

    @Test
    void deveRetornarOpcaoInvalida() throws Exception {
       
        JustificativaBD repoMock = mock(JustificativaBD.class);
        JustificativaAtraso service = new JustificativaAtraso(repoMock);

      
        // Passamos usuario(1), idAtraso(50) e código inválido(99)
        String resultado = service.registrarJustificativa(1, 50, 99);

       
        // Verifica que o método de salvar NUNCA foi chamado (com 3 parâmetros int, int, String)
        verify(repoMock, never()).salvarJustificativa(anyInt(), anyInt(), anyString());
        
        assertEquals("Opção inválida.", resultado);
    }
}