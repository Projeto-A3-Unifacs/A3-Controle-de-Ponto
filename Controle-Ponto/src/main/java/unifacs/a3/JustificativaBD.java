package unifacs.a3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JustificativaBD {
     private Connection conn;
    public JustificativaBD(Connection conn) {
        this.conn = conn;
    }
    
    public void salvarJustificativa(int usuarioId, String tipo) throws Exception {
        
        PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO justificativa(tipo, usuario_id) VALUES (?, ?)"
        );
        ps.setString(1, tipo);
        ps.setInt(2, usuarioId);
        ps.executeUpdate();
        ps.close();
        
    }
}
