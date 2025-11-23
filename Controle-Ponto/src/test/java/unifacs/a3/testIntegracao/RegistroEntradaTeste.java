package unifacs.a3.testIntegracao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import unifacs.a3.UsuarioBD;
import unifacs.a3.ConnectionManager;
import unifacs.a3.HorarioBD;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegistroEntradaTeste {

    private Connection connection;
    private int usuarioIdTeste = 9999;

    @BeforeAll
    public void conn() throws Exception {
        connection = ConnectionManager.getConnection();
        limparRegistros();
    }

    private void limparRegistros() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
            "DELETE FROM horarios WHERE usuario_id = ?"
        );
        ps.setInt(1, usuarioIdTeste);
        ps.executeUpdate();
    }

    private ResultSet buscarUltimoRegistro() throws Exception {
        PreparedStatement ps = connection.prepareStatement(
            "SELECT * FROM horarios WHERE usuario_id = ? ORDER BY id DESC LIMIT 1"
        );
        ps.setInt(1, usuarioIdTeste);
        return ps.executeQuery();
    }

    @Test
    void testeRegistroEntrada() throws Exception {
        HorarioBD HorarioBD = new HorarioBD(connection);
        LocalDateTime agora = LocalDateTime.now();
        
        boolean registrado = HorarioBD.registra_Hora(agora, usuarioIdTeste);
        Assertions.assertTrue(registrado);

        ResultSet rs = buscarUltimoRegistro();
        Assertions.assertTrue(rs.next(), "Nenhum registro encontrado no banco.");

        Timestamp entradaBD = rs.getTimestamp("entrada");

        Assertions.assertNotNull(entradaBD);
        Assertions.assertEquals(agora.getYear(), entradaBD.toLocalDateTime().getYear());
        Assertions.assertEquals(agora.getMonth(), entradaBD.toLocalDateTime().getMonth());
        Assertions.assertEquals(agora.getDayOfMonth(), entradaBD.toLocalDateTime().getDayOfMonth());
    }

    @AfterAll
    void finalizar() throws Exception {
        limparRegistros();
        connection.close();
    }
}
