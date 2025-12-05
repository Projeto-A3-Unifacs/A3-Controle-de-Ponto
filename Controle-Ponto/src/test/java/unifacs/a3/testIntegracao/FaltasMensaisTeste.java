package unifacs.a3.testIntegracao;

import org.junit.jupiter.api.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Set;

import unifacs.a3.ConnectionManager;
import unifacs.a3.HorarioTestHelper;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FaltasMensaisTeste {

    private Connection connection;
    private int usuarioIdTeste = 8888;

    @BeforeEach
    void setup() throws Exception {
        connection = ConnectionManager.getConnection();
        limparRegistros();
    }

    private void limparRegistros() throws Exception {
        PreparedStatement ps = connection.prepareStatement(
            "DELETE FROM horarios WHERE usuario_id = ?"
        );
        ps.setInt(1, usuarioIdTeste);
        ps.executeUpdate();
    }

    private void inserirEntrada(LocalDate dia) throws Exception {
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO horarios (usuario_id, entrada) VALUES (?, ?)"
        );

        ps.setInt(1, usuarioIdTeste);
        ps.setObject(2, dia.atTime(8, 0));
        ps.executeUpdate();
    }

    @Test
    void testeFaltasMensais() throws Exception {
      
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        
        var diasTrabalhados = Set.of(
            inicioMes.plusDays(0), // Veio no dia 1
            inicioMes.plusDays(2), // Veio no dia 3
            inicioMes.plusDays(4)  // Veio no dia 5
        );

        registrarPresencasNoBanco(diasTrabalhados);

        // 2. ACT: Executa o sistema
        int faltasReais = HorarioTestHelper.faltasMensais(usuarioIdTeste);

        // 3. ASSERT: Valida contra nosso "Oráculo" (Cálculo auxiliar)
        int faltasEsperadas = calcularFaltasEsperadasAteHoje(diasTrabalhados);
        
        Assertions.assertEquals(faltasEsperadas, faltasReais, 
            "O cálculo de faltas do sistema não bateu com a expectativa matemática.");
    }



    private void registrarPresencasNoBanco(java.util.Set<LocalDate> dias) throws Exception {
        for (LocalDate dia : dias) {
            inserirEntrada(dia);
        }
    }

    private int calcularFaltasEsperadasAteHoje(java.util.Set<LocalDate> diasPresenca) {
        LocalDate hoje = LocalDate.now();
        LocalDate cursor = hoje.withDayOfMonth(1);
        int contadorFaltas = 0;

        while (!cursor.isAfter(hoje)) {
            boolean ehDiaUtil = cursor.getDayOfWeek().getValue() <= 5; // 1=Seg, 5=Sex
            boolean veioTrabalhar = diasPresenca.contains(cursor);

            if (ehDiaUtil && !veioTrabalhar) {
                contadorFaltas++;
            }
            cursor = cursor.plusDays(1);
        }
        return contadorFaltas;
    }
    
    @AfterEach
    void finalizar() throws Exception {
        limparRegistros();
        connection.close();
    }
}
