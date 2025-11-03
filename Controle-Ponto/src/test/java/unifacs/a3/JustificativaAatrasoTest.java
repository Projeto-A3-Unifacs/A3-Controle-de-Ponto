package unifacs.a3;
 import org.junit.jupiter.api.Test;
    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.Mockito.*;

public class JustificativaAatrasoTest {

     @Test
        void deveRegistrarJustificativaDeAtestado() throws Exception {
            // Arrange
            JustificativaBD repoMock = mock(JustificativaBD.class);
            JustificativaAtraso service = new JustificativaAtraso(repoMock);
    
            // Act
            String resultado = service.registrarJustificativa(1, 2);
    
            // Assert
            verify(repoMock).salvarJustificativa(1, "Atestado");
            assertEquals("Justificativa registrada: Atestado", resultado);
        }
    
        
    
        @Test
        void deveRetornarOpcaoInvalida() throws Exception {
            JustificativaBD repoMock = mock(JustificativaBD.class);
            JustificativaAtraso service = new JustificativaAtraso(repoMock);
    
            String resultado = service.registrarJustificativa(1, 99);
    
            verify(repoMock, never()).salvarJustificativa(anyInt(), anyString());
            assertEquals("Opção inválida.", resultado);
        }
    }
    


