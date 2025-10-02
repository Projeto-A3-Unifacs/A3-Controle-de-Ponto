package unifacs.a3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Usuario {


  //Registra ponto de entrada
   public static boolean registra_Hora(LocalDateTime entrada, int usuario_id) throws Exception {
        Connection connect =Conexao.conexao_BD();
        boolean horario_registrado = false;

        PreparedStatement ps = connect.prepareStatement(
            "INSERT INTO horarios (usuario_id, entrada) VALUES (?, ?)"
        );
        ps.setInt(1, usuario_id);
        ps.setObject(2, entrada);
        ps.executeUpdate();

        return horario_registrado = true;
    }


  //Registra ponto de saída
    public static void registra_Saida( ) throws Exception {
           Connection connect = Conexao.conexao_BD();
                        Statement stmt = connect.createStatement();

                        // Busca o último registro sem saída
                        ResultSet rs = stmt.executeQuery(
                            "SELECT id FROM horarios WHERE saida IS NULL ORDER BY id DESC LIMIT 1"
                        );

                        if (rs.next()) {
                            int id_buscado = rs.getInt("id");

                            // Atualiza a saída no registro encontrado
                            PreparedStatement ps = connect.prepareStatement(
                                "UPDATE horarios SET saida = ? WHERE id = ?"
                            );
                            ps.setObject(1, Timestamp.valueOf(LocalDateTime.now()));
                            ps.setInt(2, id_buscado);
                            ps.executeUpdate();

                            System.out.println("Saída registrada com sucesso");
                        }
     }
  

    //Recuperação de senha

   public static void recupera_senha() throws Exception{
        Scanner scan=new Scanner(System.in);
        String email= scan.nextLine();
        Connection con= Conexao.conexao_BD();
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
    }


}
