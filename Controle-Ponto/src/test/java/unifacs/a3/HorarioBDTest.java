package unifacs.a3;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.sql.*;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class HorarioBDTest {

    
    // Teste simples do registro de hora de entrada
    @Test
    void testRegistraHoraSimples() {
        int usuarioId = 1;
        LocalDateTime entrada = LocalDateTime.now();

        // Simula o retorno do método registra_Hora
        boolean registrado = HorarioTest.RegistraHora(entrada, usuarioId);

        // Verificação
        assertTrue(registrado, "O horário deve ser registrado com sucesso");
    }


   


     // Teste simples de cálculo de faltas semanais
    @Test
    void testFaltasSemanaisSimples() {
    int faltas = HorarioTest.FaltasSemanais(1);
        assertTrue(faltas >= 0, "O número de faltas semanais não deve ser negativo.");
        System.out.println("Faltas semanais simuladas para usuário 1: " + faltas);
    }

    // Teste simples de cálculo de faltas mensais
    @Test
    void testFaltasMensaisSimples() {
        int faltas = HorarioTest.FaltasMensais(1);
        assertTrue(faltas >= 0, "O número de faltas mensais não deve ser negativo.");
        System.out.println("Faltas mensais simuladas para usuário 1: " + faltas);
    }


        // Teste simples de identificação de dias com atraso
      @Test
    void deveRetornarDiasDeAtraso() throws Exception {
        // simula o comportamento de buscarEntradas
        List<LocalDateTime> entradasMock = Arrays.asList(
            LocalDateTime.of(2024, 10, 1, 8, 30),
            LocalDateTime.of(2024, 10, 2, 7, 45),
            LocalDateTime.of(2024, 10, 3, 9, 0)
        );

        try (MockedStatic<HorarioBD> mock = mockStatic(HorarioBD.class)) {
            mock.when(() -> HorarioBD.buscarEntradasPorUsuario(1)).thenReturn(entradasMock);
            mock.when(() -> HorarioBD.verifica_atraso(1)).thenCallRealMethod();

            List<LocalDate> resultado = HorarioBD.verifica_atraso(1);

            assertEquals(Arrays.asList(
                LocalDate.of(2024, 10, 1),
                LocalDate.of(2024, 10, 3)
            ), resultado);
        }
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaAtrasos() throws Exception {
        List<LocalDateTime> entradasMock = Arrays.asList(
            LocalDateTime.of(2024, 10, 1, 7, 30),
            LocalDateTime.of(2024, 10, 2, 7, 59)
        );

        try (MockedStatic<HorarioBD> mock = mockStatic(HorarioBD.class)) {
            mock.when(() -> HorarioBD.buscarEntradasPorUsuario(2)).thenReturn(entradasMock);
            mock.when(() -> HorarioBD.verifica_atraso(2)).thenCallRealMethod();

            List<LocalDate> resultado = HorarioBD.verifica_atraso(2);

            assertTrue(resultado.isEmpty());
        }
    }

     @Test
    void registraSaida_deveAtualizarEntradaAbertaDoUsuario() throws Exception {
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        // Mock do UsuarioBD.conexao_BD()
        try (MockedStatic<UsuarioBD> usuarioBDStatic = mockStatic(UsuarioBD.class)) {
            usuarioBDStatic.when(UsuarioBD::conexao_BD).thenReturn(mockConnection);

            // Configura o ResultSet para simular uma entrada aberta do usuário
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("id")).thenReturn(123);

            // Chama o método que queremos testar
            HorarioBD.registra_Saida(4);

            // Verifica se o UPDATE foi chamado com o id correto
            verify(mockPreparedStatement).setObject(eq(1), any(Timestamp.class));
            verify(mockPreparedStatement).setInt(2, 123);
            verify(mockPreparedStatement).executeUpdate();
        }
    }


    void registraSaida_semEntradaAberta_deveMostrarMensagem() throws Exception {
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        try (MockedStatic<UsuarioBD> usuarioBDStatic = mockStatic(UsuarioBD.class)) {
            usuarioBDStatic.when(UsuarioBD::conexao_BD).thenReturn(mockConnection);

            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false);

            // Chama o método
            HorarioBD.registra_Saida(1);

            // Como não há entrada aberta, não deve chamar executeUpdate
            verify(mockPreparedStatement, never()).executeUpdate();
        }
    }
}
