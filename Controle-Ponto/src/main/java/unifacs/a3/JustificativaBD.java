package unifacs.a3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JustificativaBD {
    
    public static void justifica_atraso(int id) throws Exception {
        Scanner scan = new Scanner(System.in);
        int justificativa = scan.nextInt();
        Connection connect = UsuarioBD.conexao_BD();

        PreparedStatement pr;
        switch (justificativa) {
            case 1:
                pr = connect.prepareStatement("INSERT INTO justificativa(tipo,usuario_id) values(?,?)");
                pr.setString(1, "Falta Injustificada");
                pr.setInt(2, id);
                pr.executeUpdate();
                System.out.println("Justificativa registrada: Falta Injustificada");
                break;
            case 2:
                pr = connect.prepareStatement("INSERT INTO justificativa(tipo,usuario_id) values(?,?)");
                pr.setString(1, "Atestado");
                pr.setInt(2, id);
                pr.executeUpdate();
                System.out.println("Justificativa registrada: Atestado");
                break;
            case 3:
                pr = connect.prepareStatement("INSERT INTO justificativa(tipo,usuario_id) values(?,?)");
                pr.setString(1, "Saída Antecipada");
                pr.setInt(2, id);
                pr.executeUpdate();
                System.out.println("Justificativa registrada: Saída Antecipada");
                break;
            case 4:
                pr = connect.prepareStatement("INSERT INTO justificativa(tipo,usuario_id) values(?,?)");
                pr.setString(1, "Hora Extra");
                pr.setInt(2, id);
                pr.executeUpdate();
                System.out.println("Justificativa registrada: Hora Extra");
                break;
            default:
                System.out.println("Opção inválida.");
        }

        scan.close();
    }
}
