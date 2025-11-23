package unifacs.a3.testIntegracao;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import unifacs.a3.ConnectionManager;
import unifacs.a3.Menu;
import unifacs.a3.Usuario;
import unifacs.a3.UsuarioBD;
import unifacs.a3.UsuarioRepository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.TestInstance;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Teste de integração para a funcionalidade de justificativa de atraso via Menu (Case 3).
 *
 * Este teste simula a interação do usuário para registrar uma justificativa
 * de atraso, passando pela interface do Menu e verificando a gravação no banco.
 */
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JustificativaIntegracaoMenuTest {

    private Connection con;


    @BeforeAll
   public  void setupDatabase() throws SQLException {
        con =ConnectionManager.getConnection();
       }

    @Test
    void deveRegistrarJustificativaPassandoPeloMenuCase3() throws Exception {

        // 1) Cria um usuário de teste real
        int usuarioId = criarUsuarioDeTeste();

        // 2) Limpa justificativas anteriores desse usuário
        limparJustificativasUsuario(usuarioId);

        // 3) Prepara o objeto de usuário
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        // 4) Simula a entrada do usuário no console:
        //    3 -> opção "Registrar justificativa de atraso" no menu
        //    2 -> "Atestado" na lista de justificativas
        String entradaSimulada = "3\n2\n";

        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(entradaSimulada.getBytes()));

            // 5) Executa o Menu, que vai ler as opções como se fosse o usuário digitando
            Menu.start(usuario,con);

        } finally {
            // 6) Restaura a entrada padrão
            System.setIn(originalIn);
        }

        // 7) Verifica se a justificativa foi registrada no banco para esse usuário
     
             PreparedStatement ps = con.prepareStatement(
                     "SELECT tipo FROM justificativa WHERE usuario_id = ? ORDER BY id DESC LIMIT 1"
             );

            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();

            assertTrue(rs.next(), "Nenhuma justificativa foi encontrada no banco para o usuário!");
            assertEquals("Atestado", rs.getString("tipo"));
            
        
    }

    // ========================================================================
    // MÉTODOS DE APOIO
    // ========================================================================

    /**
     * Cria um usuário de teste real no banco de dados
     * e retorna o ID gerado automaticamente.
     */
    private int criarUsuarioDeTeste() throws Exception {
     
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO usuario(nome, email, senha) VALUES (?, ?, ?) RETURNING id"
             );

            ps.setString(1, "Usuario Teste");
            ps.setString(2, "teste_" + System.currentTimeMillis() + "@teste.com");
            ps.setInt(3, 123);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("id");
            
        
    }

    /**
     * Remove todas as justificativas do usuário informado,
     * garantindo que o teste comece com um estado limpo.
     */
    private void limparJustificativasUsuario(int usuarioId) throws Exception {
        
             PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM justificativa WHERE usuario_id = ?");

            ps.setInt(1, usuarioId);
            ps.executeUpdate();
        
    }

    @AfterAll
   void fecharConexao() throws SQLException {
       
      
        con.close();
    }
}
