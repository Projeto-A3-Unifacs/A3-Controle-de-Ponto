package unifacs.a3.testIntegracao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.checkerframework.checker.units.qual.h;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import unifacs.a3.ConnectionManager;
import unifacs.a3.HorarioBD;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistroSaidaTeste {

    private Connection connection;
    private int usuarioIdTeste = 9999;

    @BeforeAll
    public void setup() throws Exception {
        connection = ConnectionManager.getConnection();
        limparRegistros();
        inserirEntradaSemSaida();
    }

    private void limparRegistros() throws Exception {
        PreparedStatement ps = connection.prepareStatement(
            "DELETE FROM horarios WHERE usuario_id = ?"
        );
        ps.setInt(1, usuarioIdTeste);
        ps.executeUpdate();
    }

    private void inserirEntradaSemSaida() throws Exception {
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO horarios (usuario_id, entrada) VALUES (?, ?)"
        );
        ps.setInt(1, usuarioIdTeste);
        ps.setObject(2, LocalDateTime.now().minusMinutes(5));
        ps.executeUpdate();
    }

    private ResultSet buscarRegistroAberto() throws Exception {
        PreparedStatement ps = connection.prepareStatement(
            "SELECT * FROM horarios WHERE usuario_id = ? ORDER BY id DESC LIMIT 1"
        );
        ps.setInt(1, usuarioIdTeste);
        return ps.executeQuery();
    }

    @Test
    void testeRegistroSaida() throws Exception {
        HorarioBD HorarioBD = new HorarioBD(connection);
        HorarioBD.registra_Saida(usuarioIdTeste);

        ResultSet rs = buscarRegistroAberto();
        Assertions.assertTrue(rs.next(), "Nenhum registro foi encontrado.");

        Timestamp saidaBD = rs.getTimestamp("saida");

        Assertions.assertNotNull(saidaBD, "A saída não foi registrada no banco.");
    }

    @AfterAll
    void finalizar() throws Exception {
        limparRegistros();
        connection.close();
    }
}
