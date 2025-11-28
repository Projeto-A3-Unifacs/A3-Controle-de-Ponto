package unifacs.a3;

import java.sql.Connection;
import java.sql.PreparedStatement;


public class JustificativaBD {
     private Connection conn;
    public JustificativaBD(Connection conn) {
        this.conn = conn;
    }
    public void salvarJustificativa(int usuarioId, int horarioId, String tipo) throws Exception {
    String sql = "INSERT INTO justificativa(tipo, usuario_id, horario_id) VALUES (?, ?, ?)";
    
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, tipo);
        ps.setInt(2, usuarioId);
        ps.setInt(3, horarioId); // Salva o ID do dia específico
        ps.executeUpdate();
    }
}

}
