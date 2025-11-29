package unifacs.a3.testIntegracao;

import org.junit.jupiter.api.*;
import unifacs.a3.ConnectionManager;
import unifacs.a3.Menu;
import unifacs.a3.Usuario;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JustificativaIntegracaoMenuTest {

    private Connection con;

    @BeforeAll
    void setupDatabase() throws SQLException {
        con = ConnectionManager.getConnection();
    }

    @Test
    void deveRegistrarJustificativaPassandoPeloMenuCase3() throws Exception {
        // --- 1. ARRANGE (PREPARAÇÃO) ---
        int usuarioId = prepararUsuarioDeTeste(); // Cria user e limpa dados antigos
        int idAtraso = criarAtrasoNoBanco(usuarioId); // Cria o "problema" para resolver
        Usuario usuarioLogado = montarObjetoUsuario(usuarioId);

        // --- 2. ACT (AÇÃO) ---
        // Simula: Menu(3) -> ID do Atraso -> Motivo Atestado(2) -> Sair(0)
        String inputUsuario = "3\n" + idAtraso + "\n2\n0\n";
        
        executarMenuComInputs(usuarioLogado, inputUsuario);

        // --- 3. ASSERT (VALIDAÇÃO) ---
        JustificativaDTO resultado = buscarUltimaJustificativa(usuarioId);

        assertNotNull(resultado, "Deveria ter gravado uma justificativa.");
        assertEquals("Atestado", resultado.tipo);
        assertEquals(idAtraso, resultado.horarioId, "A justificativa foi ligada ao atraso errado.");
    }

    // ========================================================================
    // 🛠️ MÉTODOS AUXILIARES (A "SUJEIRA" FICA AQUI EMBAIXO)
    // ========================================================================

    private void executarMenuComInputs(Usuario usuario, String inputs) {
        InputStream originalIn = System.in;
        try {
            System.setIn(new ByteArrayInputStream(inputs.getBytes()));
            Menu.start(usuario, con);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao executar Menu no teste", e);
        } finally {
            System.setIn(originalIn);
        }
    }

    private int prepararUsuarioDeTeste() throws Exception {
        // Garante estado limpo
        deletarUsuarioPeloEmail("teste_integracao@teste.com");
        
        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO usuario(nome, email, senha) VALUES (?, ?, ?) RETURNING id"
        );
        ps.setString(1, "Usuario Teste");
        ps.setString(2, "teste_integracao@teste.com");
        ps.setInt(3, 123);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("id");
    }

    private int criarAtrasoNoBanco(int usuarioId) throws Exception {
        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO horarios(usuario_id, entrada) VALUES (?, ?) RETURNING id"
        );
        ps.setInt(1, usuarioId);
        // Cria atraso às 09:00
        ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now().withHour(9).withMinute(0)));
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt("id");
    }

    private Usuario montarObjetoUsuario(int id) {
        Usuario u = new Usuario();
        u.setId(id);
        u.setNome("Usuario Teste");
        return u;
    }

    // Classe interna simples só para transportar o resultado do banco
    class JustificativaDTO {
        String tipo;
        int horarioId;
    }

    private JustificativaDTO buscarUltimaJustificativa(int usuarioId) throws SQLException {
        PreparedStatement ps = con.prepareStatement(
            "SELECT tipo, horario_id FROM justificativa WHERE usuario_id = ? ORDER BY id DESC LIMIT 1"
        );
        ps.setInt(1, usuarioId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            JustificativaDTO dto = new JustificativaDTO();
            dto.tipo = rs.getString("tipo");
            dto.horarioId = rs.getInt("horario_id");
            return dto;
        }
        return null;
    }

    // Limpeza robusta
    private void deletarUsuarioPeloEmail(String email) throws SQLException {
        PreparedStatement psBusca = con.prepareStatement("SELECT id FROM usuario WHERE email = ?");
        psBusca.setString(1, email);
        ResultSet rs = psBusca.executeQuery();
        
        if (rs.next()) {
            int id = rs.getInt("id");
            // Limpa dependências
            con.prepareStatement("DELETE FROM justificativa WHERE usuario_id = " + id).executeUpdate();
            con.prepareStatement("DELETE FROM horarios WHERE usuario_id = " + id).executeUpdate();
            con.prepareStatement("DELETE FROM usuario WHERE id = " + id).executeUpdate();
        }
    }

    @AfterAll
    void tearDown() throws SQLException {
        deletarUsuarioPeloEmail("teste_integracao@teste.com");
        if (con != null && !con.isClosed()) con.close();
    }
}