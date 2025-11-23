package unifacs.a3.testIntegracao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import unifacs.a3.ConnectionManager;
import unifacs.a3.HorarioBD;
import unifacs.a3.Usuario;
import unifacs.a3.UsuarioBD;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VerificaAtrasoTeste {

    private Connection connection;
    private int usuarioIdTeste;

    @BeforeAll
    public void setup() throws Exception {
        connection = ConnectionManager.getConnection();
        limparRegistros();
        criarUsuarioDeTeste();
    }

    

    private void limparRegistros() throws SQLException {
        PreparedStatement ps1 = connection.prepareStatement("DELETE FROM horarios WHERE usuario_id = ?");
        ps1.setInt(1, usuarioIdTeste);
        ps1.executeUpdate();
        ps1.close();

        PreparedStatement ps2 = connection.prepareStatement("DELETE FROM usuario WHERE email = ?");
        ps2.setString(1, "atraso_teste@gmail.com");
        ps2.executeUpdate();
        ps2.close();
    }

    private void criarUsuarioDeTeste() throws Exception {
        Usuario u = new Usuario();
        u.setNome("usuario_atraso");
        u.setEmail("atraso_teste@gmail.com");
        u.setSenha(123456);

        // cadastra no banco
        new UsuarioBD(connection).cadastra_Usuario(u);

        // buscar ID gerado
        PreparedStatement ps = connection.prepareStatement("SELECT id FROM usuario WHERE email = ?");
        ps.setString(1, "atraso_teste@gmail.com");
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            usuarioIdTeste = rs.getInt("id");
        }

        rs.close();
        ps.close();
    }

    private void inserirEntrada(LocalDateTime entrada) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO horarios (usuario_id, entrada) VALUES (?, ?)"
        );
        ps.setInt(1, usuarioIdTeste);
        ps.setTimestamp(2, Timestamp.valueOf(entrada));
        ps.executeUpdate();
        ps.close();
    }



    @Test
    void testeVerificarAtrasos() throws Exception {
HorarioBD HorarioBD = new HorarioBD(connection);
        // Entrada sem atraso: 07:55
        inserirEntrada(LocalDateTime.of(2025, 1, 10, 7, 55));

        // Entrada com atraso: 08:15
        inserirEntrada(LocalDateTime.of(2025, 1, 11, 8, 15));

        // Chama método sendo testado
        List<LocalDate> atrasos = HorarioBD.verifica_atraso(usuarioIdTeste);

        // Deve ter 1 atraso
        Assertions.assertEquals(1, atrasos.size());

        // O atraso deve ser do dia 11
        Assertions.assertTrue(
            atrasos.contains(LocalDate.of(2025, 1, 11)),
            "A data do atraso deveria ser 11/01/2025"
        );
    }

    

    @AfterAll
    void finalizar() throws Exception {
        limparRegistros();
        connection.close();
    }
}
