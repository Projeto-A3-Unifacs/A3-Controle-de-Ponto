package unifacs.a3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepository {

     public boolean existePorEmail(String email) throws SQLException {
        Connection con = UsuarioBD.conexao_BD();
        String sql = "SELECT 1 FROM usuario WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        boolean existe = rs.next();
        rs.close();
        ps.close();
        con.close();
        return existe;
    }

    public void atualizarSenha(String email, int novaSenha) throws SQLException {
        Connection con = UsuarioBD.conexao_BD();
        String sql = "UPDATE usuario SET senha = ? WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, novaSenha);
        ps.setString(2, email);
        ps.executeUpdate();
        ps.close();
        con.close();
    }

}
