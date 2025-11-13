package unifacs.a3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UsuarioBD {
    
     public static Connection conexao_BD()  {
        String url = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?sslmode=require";
        String user = "postgres.iczuamsiqqdvifexshbf";
        String password = "miniProject25@";
       try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // ou lance a exceção para tratar fora
        }
    }
//cadastro de usuário
    public  boolean cadastra_Usuario(Usuario usuario) throws Exception {
        Connection con = conexao_BD();
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
        Connection con = conexao_BD();

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

        rs.close();
        ps.close();
        con.close();

        return usuario;
    }



   
}
