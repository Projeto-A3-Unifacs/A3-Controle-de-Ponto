package unifacs.a3.testIntegracao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import unifacs.a3.Usuario;
import unifacs.a3.UsuarioBD;
import unifacs.a3.UsuarioRepository;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CadastraTeste {

 private Connection connection;
 private UsuarioRepository repositorio;

    @BeforeAll
   public  void setupDatabase() throws SQLException {
        connection = UsuarioBD.conexao_BD();
        repositorio = new UsuarioRepository(connection);
        deletarUsuarioDeTeste();

    }


  
private void deletarUsuarioDeTeste() throws SQLException {
    PreparedStatement ps = connection.prepareStatement(
        "DELETE FROM usuario WHERE email = ? AND nome = ? AND senha = ?"
    );
    ps.setString(1, "teste_integracao@gmail.com");
    ps.setString(2, "teste_integração");
    ps.setInt(3, 123456);
    ps.executeUpdate();
}

private Usuario buscarUsuarioPorEmail(String email) throws SQLException {
    PreparedStatement ps = connection.prepareStatement("SELECT * FROM usuario WHERE email = ?");
    ps.setString(1, email);
    ResultSet rs = ps.executeQuery();

    if (!rs.next()) return null;

        Usuario u = new Usuario();
        u.setNome(rs.getString("nome"));
        u.setEmail(rs.getString("email"));
        u.setSenha(rs.getInt("senha"));
        return u;
    }


 @Test
 void testeCadastroUsuario() throws Exception {
    Usuario usuarioTeste = new Usuario();
        usuarioTeste.setNome("teste_integração");
        usuarioTeste.setSenha(123456);
        usuarioTeste.setEmail("teste_integracao@gmail.com");

        new UsuarioBD().cadastra_Usuario(usuarioTeste);

        Usuario encontrado = buscarUsuarioPorEmail("teste_integracao@gmail.com");

        Assertions.assertNotNull(encontrado);
        Assertions.assertEquals("teste_integração", encontrado.getNome());
        Assertions.assertEquals("teste_integracao@gmail.com", encontrado.getEmail());
        Assertions.assertEquals(123456, encontrado.getSenha());  


 }
    @AfterAll
   void fecharConexao() throws SQLException {
       
        deletarUsuarioDeTeste();
        connection.close();
    }

}
