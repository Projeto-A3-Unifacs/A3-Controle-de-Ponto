package unifacs.a3.testIntegracao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import unifacs.a3.ConnectionManager;
import unifacs.a3.Usuario;
import unifacs.a3.UsuarioBD;
import unifacs.a3.UsuarioRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VerifcaUserTest {
 private Connection connection;
 private UsuarioRepository repositorio;

    @BeforeAll
   public  void setupDatabase() throws SQLException {
        connection =  ConnectionManager.getConnection();
        repositorio = new UsuarioRepository(connection);
        deletarUsuarioDeTeste();

    }


  
    private void deletarUsuarioDeTeste() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
            "DELETE FROM usuario WHERE email = ? AND nome = ? AND senha = ?"
        );
        ps.setString(1, "nica@gmail.com");
        ps.setString(2, "Nicole Silva");
        ps.setInt(3, 456);
        ps.executeUpdate();
    }
    
    private void inserindoUsuarioDeTeste(Usuario user) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
            "INSERT INTO usuario (nome, senha, email) VALUES (?, ?, ?)"
        );
        ps.setString(1, user.getNome());
        ps.setInt(2, user.getSenha());
        ps.setString(3, user.getEmail());
        ps.executeUpdate();
    }

    @Test
    void testeVerificaUsuario() throws Exception {
    // Cria a conexão real com o banco
    try (Connection conn = ConnectionManager.getConnection()) {

        // Instancia o DAO com a conexão
        UsuarioBD usuarioBD = new UsuarioBD(conn);

        // Cria usuário de teste
        Usuario usuarioTeste = new Usuario();
        usuarioTeste.setNome("Nicole Silva");
        usuarioTeste.setSenha(456);
        usuarioTeste.setEmail("nica@gmail.com");

        // Insere usuário no BD (pode ser um método auxiliar que usa o mesmo DAO)
        usuarioBD.cadastra_Usuario(usuarioTeste);

        // Verifica usuário usando o DAO com conexão
        Usuario usuarioVerificado = usuarioBD.verificaUser(usuarioTeste);

        // Validações
        assert usuarioVerificado != null;
        assert usuarioVerificado.getNome().equals("Nicole Silva");
        assert usuarioVerificado.getSenha() == 456;
        assert usuarioVerificado.getEmail().equals("nica@gmail.com");
    }
}
}
