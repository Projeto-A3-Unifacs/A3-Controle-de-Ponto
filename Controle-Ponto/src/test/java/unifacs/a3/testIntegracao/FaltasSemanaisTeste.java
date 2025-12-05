package unifacs.a3.testIntegracao;

import org.junit.jupiter.api.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Set;

import unifacs.a3.ConnectionManager;
import unifacs.a3.HorarioTestHelper;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FaltasSemanaisTeste {

    private Connection connection;
    private int usuarioIdTeste = 7777;

    @BeforeEach
    void setup() throws Exception {
        connection = ConnectionManager.getConnection();
        limparRegistros();
    }

    @Test
    void testeFaltasSemanais() throws Exception {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioSemana = hoje.minusDays(7);

    
        var diasTrabalhados = Set.of(
            inicioSemana.plusDays(0), 
            inicioSemana.plusDays(2),
            inicioSemana.plusDays(4)
        );

        registrarPresencasNoBanco(diasTrabalhados);

        int faltasReais = HorarioTestHelper.faltasSemanais(usuarioIdTeste);

        int faltasEsperadas = calcularFaltasEsperadasSemana(diasTrabalhados);

        Assertions.assertEquals(
            faltasEsperadas,
            faltasReais,
            "O cálculo de faltas semanais não bateu com a expectativa matemática."
        );
    }

 

    private void registrarPresencasNoBanco(Set<LocalDate> dias) throws Exception {
        for (LocalDate dia : dias) {
            inserirEntrada(dia);
        }
    }

    private int calcularFaltasEsperadasSemana(Set<LocalDate> diasPresenca) {
        LocalDate hoje = LocalDate.now();
        LocalDate cursor = hoje.minusDays(7); // Janela de 7 dias atrás
        int contadorFaltas = 0;

        while (!cursor.isAfter(hoje)) {
            boolean ehDiaUtil = cursor.getDayOfWeek().getValue() <= 5; // 1=Seg, 5=Sex
            boolean veioTrabalhar = diasPresenca.contains(cursor);

            // Só conta falta se for dia útil E ele NÃO veio
            if (ehDiaUtil && !veioTrabalhar) {
                contadorFaltas++;
            }
            cursor = cursor.plusDays(1);
        }
        return contadorFaltas;
    }

    private void inserirEntrada(LocalDate dia) throws Exception {
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO horarios (usuario_id, entrada) VALUES (?, ?)"
        );
        ps.setInt(1, usuarioIdTeste);
        ps.setObject(2, dia.atTime(8, 0)); // Define hora fixa 08:00
        ps.executeUpdate();
    }

    private void limparRegistros() throws Exception {
        PreparedStatement ps = connection.prepareStatement(
            "DELETE FROM horarios WHERE usuario_id = ?"
        );
        ps.setInt(1, usuarioIdTeste);
        ps.executeUpdate();
    }

    @AfterEach
    void finalizar() throws Exception {
        limparRegistros();
        connection.close();
    }
}