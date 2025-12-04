package unifacs.a3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.jupiter.api.Test;

public class JustificativaFaltaTest {

    @Test
    void testRegistraFaltaSimples() throws Exception {

        // Mock da conexão e do PreparedStatement
        Connection conMock = mock(Connection.class);
        PreparedStatement psMock = mock(PreparedStatement.class);

        // Configura o comportamento da conexão mockada
        when(conMock.prepareStatement("INSERT INTO faltas (motivo, usuario_id) VALUES (?, ?)"))
                .thenReturn(psMock);

        // Instância real da classe que estamos testando
        FaltaBD repo = new FaltaBD(conMock);

        // Chama o método a ser testado
        repo.registra_faltaBD(10, "Falecimento");

        // Verifica se chamou o SQL correto
        verify(conMock).prepareStatement("INSERT INTO faltas (motivo, usuario_id) VALUES (?, ?)");
        verify(psMock).setString(1, "Falecimento");
        verify(psMock).setInt(2, 10);
        verify(psMock).executeUpdate();
        verify(psMock).close();
    }
}
