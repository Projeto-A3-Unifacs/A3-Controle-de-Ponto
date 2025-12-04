package unifacs.a3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.MockedStatic.Verification;

public class FaltaBDTest {

    @Test
    void testRegistraFaltaSimples(Verification UsuarioBD) throws Exception {

        // Mock da conexão e do PreparedStatement
        Connection conMock = mock(Connection.class);
        PreparedStatement psMock = mock(PreparedStatement.class);

        // Configura o comportamento da conexão mockada
        when(conMock.prepareStatement(anyString())).thenReturn(psMock);

        // Mock estático para simular UsuarioBD.conexao_BD()
        try (MockedStatic<UsuarioBD> mockedStatic = mockStatic(UsuarioBD.class)) {

            mockedStatic.when(UsuarioBD).thenReturn(conMock);

            // Instância real da classe que estamos testando
            FaltaBD repo = new FaltaBD();

            // Chama o método a ser testado
            repo.registra_faltaBD(10, "Falecimento");

            // Verifica se chamou o SQL correto
            verify(conMock).prepareStatement("INSERT INTO Faltas(motivo, usuario_id) VALUES (?, ?)");
            verify(psMock).setString(1, "Falecimento");
            verify(psMock).setInt(2, 10);
            verify(psMock).executeUpdate();
            verify(psMock).close();
            verify(conMock).close();
        }
    }
}
