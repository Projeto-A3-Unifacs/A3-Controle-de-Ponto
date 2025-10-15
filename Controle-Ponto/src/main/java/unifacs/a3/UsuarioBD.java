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
    public static void cadastra_Usuario(String nome,int senha) throws Exception {
          try (Connection conectando = conexao_BD()) {
                    PreparedStatement ps = conectando.prepareStatement(
                        "INSERT INTO usuario (nome, senha) VALUES (?, ?)"
                    );
                    ps.setString(1,nome);
                    ps.setInt(2, senha);
                    ps.executeUpdate();
                }

                System.out.println("Usuário cadastrado com sucesso!");
    }

  //verificação de usuário
    public static Usuario verificaUser(String nome_user, int senha_user) throws Exception {
        Connection con = conexao_BD();

        String sql = "SELECT id, nome, email, senha FROM usuario WHERE nome = ? AND senha = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, nome_user);
        ps.setInt(2, senha_user);

        ResultSet rs = ps.executeQuery();

        Usuario usuario = null;

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

//Recuperação de senha

   public static void recupera_senha() throws Exception{
        Scanner scan=new Scanner(System.in);
        String email= scan.nextLine();
        Connection con= conexao_BD();
       String sql = "SELECT  FROM usuario WHERE email = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ResultSet rs=ps.executeQuery();
        if(rs.next()){
            System.out.println("Digite sua nova senha:");
            int senha_nova=scan.nextInt();
            ps=con.prepareStatement("UPDATE usuario set senha=? where email=?");
            ps.setInt(1,senha_nova);
            ps.setString(2,email);
            ps.executeUpdate();
             System.out.println("Senha alterada com sucesso!");
        }else{
            System.out.println("Usuário não encontrado!");
        
        }

    rs.close();
    ps.close();
    con.close();
    scan.close();
    }


}
