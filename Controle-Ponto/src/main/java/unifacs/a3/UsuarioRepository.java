package unifacs.a3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {
     private final Connection con;

    public UsuarioRepository(Connection con) {
        this.con = con;
    }



     public boolean existePorEmail(String email) throws SQLException {
       
        String sql = "SELECT 1 FROM usuario WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        boolean existe = rs.next();
        return existe;
    }

    public void atualizarSenha(String email, int novaSenha) throws SQLException {
        
        String sql = "UPDATE usuario SET senha = ? WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, novaSenha);
        ps.setString(2, email);
        ps.executeUpdate();
        
    }

}
