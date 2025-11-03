package unifacs.a3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JustificativaBD {
    
    public void salvarJustificativa(int usuarioId, String tipo) throws Exception {
        Connection con = UsuarioBD.conexao_BD();
        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO justificativa(tipo, usuario_id) VALUES (?, ?)"
        );
        ps.setString(1, tipo);
        ps.setInt(2, usuarioId);
        ps.executeUpdate();
        ps.close();
        con.close();
    }
}
