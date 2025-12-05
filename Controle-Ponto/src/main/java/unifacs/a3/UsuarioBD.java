package unifacs.a3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UsuarioBD {
    
   private Connection con;
    public UsuarioBD(Connection con) {
        this.con = con;
    }
    
    
//cadastro de usuário
    public  boolean cadastra_Usuario(Usuario usuario) throws Exception {
       
                    PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO usuario (nome, senha,email) VALUES (?, ?,?)"
                    );
                    ps.setString(1,usuario.getNome());
                    ps.setInt(2,usuario.getSenha());
                    ps.setString(3,usuario.getEmail());
                    ps.executeUpdate();
                

                System.out.println("Usuário cadastrado com sucesso!");
                return true;
    }

  //verificação de usuário
    public  Usuario verificaUser(Usuario usuario) throws Exception {
        

        String sql = "SELECT id, nome, email, senha FROM usuario WHERE nome = ? AND senha = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, usuario.getNome());
        int senha_user= usuario.getSenha();
        ps.setInt(2, senha_user);

        ResultSet rs = ps.executeQuery();

         usuario = null;

        if (rs.next()) {
            usuario = new Usuario();
            usuario.setId(rs.getInt("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setEmail(rs.getString("email"));
            usuario.setSenha(rs.getInt("senha"));
        }

      

        return usuario;
    }


    public static Connection conexao_BD() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'conexao_BD'");
    }



   
}
