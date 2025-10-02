package unifacs.a3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

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
    public static void cadastra_Usuario(String nome, int senha ) throws Exception {
          try (Connection conectando = Conexao.conexao_BD()) {
                    PreparedStatement ps = conectando.prepareStatement(
                        "INSERT INTO usuario (nome, senha) VALUES (?, ?)"
                    );
                    ps.setString(1, nome);
                    ps.setInt(2, senha);
                    ps.executeUpdate();
                }
    }

  //verificação de usuário
    public static Resultado verificaUser(String nome_user, int senha_user) throws Exception {
        Connection connect =Conexao.conexao_BD();
        boolean user_cadastrado = false;
        int id = 0;

        String sql = "SELECT id FROM usuario WHERE nome = ? AND senha = ?";
        PreparedStatement ps = connect.prepareStatement(sql);
        ps.setString(1, nome_user);
        ps.setInt(2, senha_user);

        ResultSet rs = ps.executeQuery();

        // Se encontrou uma linha, usuário existe
        if (rs.next()) {
            user_cadastrado = true;
            id = rs.getInt("id");
        
        }

        return new Resultado(id, user_cadastrado);
    }


}
