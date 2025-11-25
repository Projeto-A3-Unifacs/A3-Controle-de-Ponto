package unifacs.a3;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class FaltaBD {

    public FaltaBD(Connection con) {
        //TODO Auto-generated constructor stub
    }

    public void registra_faltaBD(int id, String motivo) throws Exception {
        Connection con = UsuarioBD.conexao_BD();

        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO Faltas (motivo, usuario_id) VALUES (?, ?)"
        );

        ps.setString(1, motivo);
        ps.setInt(2, id);
        ps.executeUpdate();

        ps.close();
        con.close();
    }
}
