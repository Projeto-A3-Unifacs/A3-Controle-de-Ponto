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


}
