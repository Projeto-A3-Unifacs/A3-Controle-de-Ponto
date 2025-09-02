package unifacs.a3;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.*;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
      
        Scanner scan=new Scanner(System.in);
        System.out.println( "Selecione uma opção:\n1-Registrar ponto\n2-Cadastrar Funcionario\n3-Recuperar acesso" );
        int opcao=scan.nextInt();
        boolean user_cadastrado=false;
        switch (opcao) {
            case 1:
            int id=0;
             System.out.println("Digite seu  nome:");
             scan.nextLine();
              String nome_user= scan.nextLine();
                System.out.println("Digite sua senha senha:");
                int senha_user=scan.nextInt();
                Connection connect=conexao_BD();
                Statement stmt=connect.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM usuario");
                while (rs.next()) {
                if(rs.getString("nome").equals(nome_user) && senha_user==rs.getInt("senha") ){
                 user_cadastrado=true;
                  id=rs.getInt("id");

                }else{
                     System.out.println("Usuario não cadastrado")  ;           //rs.getString("nome") ;
            }
        }        
            if(user_cadastrado)
            System.out.println("1-Entrada\n2-Saída");
            int resposta=scan.nextInt();
             if(resposta==1){
                LocalDateTime entrada= LocalDateTime.now();
                  PreparedStatement ps = connect.prepareStatement("INSERT INTO horarios (usuario_id, entrada) VALUES (?, ?)"); 
            ps.setInt(1, id);
           ps.setObject(2, entrada);
            ps.executeUpdate();

             }else{
                //LocalDateTime saida= LocalDateTime.now();
                 rs = stmt.executeQuery("SELECT id FROM horarios WHERE saida IS NULL ORDER BY id DESC LIMIT 1");
                 if (rs.next()){
                    int id_buscado=rs.getInt("id");
                  PreparedStatement   ps = connect.prepareStatement("UPDATE horarios SET saida = ? WHERE id = ?");
                   ps.setObject(1,Timestamp.valueOf(LocalDateTime.now()));
                   ps.setInt(2, id_buscado);
                    ps.executeUpdate();


                   //System.out.println(saida);
                   System.out.println("Update realizado com sucesso!");
                   System.out.println(id_buscado);

                    
                 }

             }

            
        
                break;
            case 2:
             System.out.println("Digite o nome do funcionário:");
              scan.nextLine();
              String nome= scan.nextLine();
               
                System.out.println("Crie uma nova senha:");
                int senha=scan.nextInt();
                
                try(Connection conectando= conexao_BD()){
                PreparedStatement ps = conectando.prepareStatement("INSERT INTO usuario (nome, senha) VALUES (?, ?)"); 
            ps.setString(1, nome);
            ps.setInt(2, senha);
            ps.executeUpdate();
        
    }
            break;

            case 3:
            System.out.println("Ainda não implementado");
            break;
            default:
                break;


        }

        scan.close();
    }

    public static Connection conexao_BD(){
     String url = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?sslmode=require";
String user = "postgres.iczuamsiqqdvifexshbf";
String password = "miniProject25@";
   Connection conn=null;
       try {
            // O Driver é carregado automaticamente pelo Maven, não precisa Class.forName()
             conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão bem-sucedida com o PostgreSQL!");
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
   return conn;
    }
}
