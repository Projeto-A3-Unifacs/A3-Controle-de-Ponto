package unifacs.a3.testIntegracao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import unifacs.a3.UsuarioRepository;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RecuperaSenhaTest {


    private Connection connection;
    private UsuarioRepository repositorio;

    @BeforeAll
    void setupDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;MODE=PostgreSQL;DB_CLOSE_DELAY=-1");
        repositorio = new UsuarioRepository(connection);

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE usuario (
                    id SERIAL PRIMARY KEY,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    senha INT NOT NULL
                )
            """);
        }
    }

    @BeforeEach
    void popularBanco() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("TRUNCATE TABLE usuario");
            stmt.execute("INSERT INTO usuario (email, senha) VALUES ('ana@email.com', 123), ('joao@email.com', 456)");
        }
    }

    @Test
    void deveRetornarTrueQuandoEmailExiste() throws SQLException {
        assertTrue(repositorio.existePorEmail("ana@email.com"));
    }

    @Test
    void deveRetornarFalseQuandoEmailNaoExiste() throws SQLException {
        assertFalse(repositorio.existePorEmail("naoexiste@email.com"));
    }

    @Test
    void deveAtualizarSenhaCorretamente() throws SQLException {
        repositorio.atualizarSenha("ana@email.com", 999);

        try (PreparedStatement ps = connection.prepareStatement("SELECT senha FROM usuario WHERE email = ?")) {
            ps.setString(1, "ana@email.com");
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                int senha = rs.getInt("senha");
                assertEquals(999, senha);
            }
        }
    }

    @AfterAll
    void fecharConexao() throws SQLException {
        connection.close();
    }
}


