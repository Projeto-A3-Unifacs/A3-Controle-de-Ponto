package unifacs.a3.testIntegracao;

import org.junit.jupiter.api.*;
import java.sql.*;
import java.time.LocalDate;

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

        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.withDayOfMonth(1);

        inserirEntrada(inicioMes.plusDays(0));
        inserirEntrada(inicioMes.plusDays(2));
        inserirEntrada(inicioMes.plusDays(4));

        int diasUteisTotais = 0;
        LocalDate dia = inicioMes;

        while (!dia.isAfter(hoje)) {
            if (dia.getDayOfWeek().getValue() <= 5) {
                diasUteisTotais++;
            }
            dia = dia.plusDays(1);
        }

        int faltasEsperadas = diasUteisTotais - 3;

        int faltasCalculadas = HorarioTestHelper.faltasMensais(usuarioIdTeste);

        Assertions.assertEquals(
            faltasEsperadas,
            faltasCalculadas,
            "O total de faltas mensais está incorreto."
        );
    }

    @AfterEach
    void finalizar() throws Exception {
        limparRegistros();
        connection.close();
    }
}
