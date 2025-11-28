package unifacs.a3;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class FaltaBD {


     private Connection con;
    public FaltaBD(Connection con) {
        this.con = con;
    }

    public void registra_faltaBD(int id, String motivo) throws Exception {
        ;

        PreparedStatement ps = con.prepareStatement(
            "INSERT INTO faltas (motivo, usuario_id) VALUES (?, ?)"
        );

        ps.setString(1, motivo);
        ps.setInt(2, id);
        ps.executeUpdate();

        ps.close();
        
    }
}
