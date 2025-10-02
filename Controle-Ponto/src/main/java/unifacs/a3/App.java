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
public class App {

    public static void main(String[] args) throws Exception {

       int alternativa=0;
        Scanner scan = new Scanner(System.in);
      
        //Acesso ao Sistema
      
            
        
        System.out.println("Digite seu nome:");
                // consumir quebra de linha
                String nome_user = scan.nextLine();
                
                System.out.println("Digite sua senha:");
                int senha_user = scan.nextInt();

                // Verifica se o usuário existe no BD
                Resultado r1 = Usuario.verificaUser(nome_user, senha_user);
         if (r1.user_encontrado()) {
        // Menu inicial
        System.out.println("Selecione uma opção:\n1-Registrar ponto\n2-Cadastrar Funcionario\n3-Registrar justificativa de atraso\n5-verificar atrasos");
        int opcao = scan.nextInt();

        //--------------------------------------------------------------
        switch (opcao) {

            // ------------------------- CASE 1: Registrar ponto -------------------------
            case 1:
                 System.out.println("1-Entrada\n2-Saída");
                    int resposta = scan.nextInt();

                    // ----------------- REGISTRA ENTRADA -----------------
                    if (resposta == 1) {
                        LocalDateTime entrada = LocalDateTime.now();
                        boolean registrado =Usuario.registra_Hora(entrada, r1.id_user());

                        if (registrado) {
                            System.out.println("Sua entrada foi registrada com sucesso");
                        }

                    // ----------------- REGISTRA SAÍDA -----------------
                    } else {
                        Usuario.registra_Saida( );
                        System.out.println("Sua saída foi registrada com sucesso");
                    }
                
                    break;

            // ------------------------- CASE 2: Cadastrar funcionário -------------------------
            case 2:
                System.out.println("Digite o nome do funcionário:");
                scan.nextLine();
                String nome = scan.nextLine();

                System.out.println("Crie uma nova senha:");
                int senha = scan.nextInt();
                Usuario.cadastra_Usuario(nome, senha);
                break;

            // ------------------------- CASE 3: Justificar atraso -------------------------
            case 3:
               System.out.println("Selecione o código de justificativa:");
                System.out.println("1 - Falta Injustificada");
                System.out.println("2 - Atestado");
                System.out.println("3 - Saída Antecipada");
                System.out.println("4 - Hora Extra");

                justifica_atraso(r1.id_user());

               break;

                 // ------------------------- CASE 5: Verificar Atrasos -------------------------

            case 5:
               System.out.println("Verificando atrasos...");
               verifica_atraso(r1.id_user());
            break;
            

        }

        
        }else{
            System.out.println("Usuário não cadastrado");
            System.out.println("1-Recuperar senha\n2-Sair");
            alternativa=scan.nextInt();
            if(alternativa==1){
                System.out.println("digite seu email:");
                Usuario.recupera_senha();

            }
        
// ------------------------- CASE 4: Registrar ponto -------------------------
            case 4:
                
// Conta faltas semanais
                
public static int faltasSemanais(int usuarioId) throws Exception {
    Connection con = conexao_BD();

    // Pegamos a data de hoje e voltamos 7 dias
    
    LocalDate hoje = LocalDate.now();
    LocalDate semanaPassada = hoje.minusDays(7);

    String sql = "SELECT DISTINCT DATE(entrada) as dia FROM horarios WHERE usuario_id =
        AND entrada BETWEEN ps.setObject(2, primeiroDia.atStartOfDay()) AND ps.setObject(3, hoje.atTime(23,59,59))";";
    PreparedStatement ps = con.prepareStatement(sql);
    
    ps.setInt(1, usuarioId);
    ps.setObject(2, semanaPassada.atStartOfDay());
    ps.setObject(3, hoje.atTime(23,59,59));
    ResultSet rs = ps.executeQuery();

    // Guardamos os dias que o usuário compareceu
    java.util.Set<LocalDate> diasTrabalhados = new java.util.HashSet<>();
    while (rs.next()) {
        diasTrabalhados.add(rs.getDate("dia").toLocalDate());
    }

    // Agora verificamos de segunda a sexta dessa semana
    int faltas = 0;
    LocalDate dia = semanaPassada;
    while (!dia.isAfter(hoje)) {
        if (dia.getDayOfWeek() != DayOfWeek.SATURDAY && dia.getDayOfWeek() != DayOfWeek.SUNDAY) {
            if (!diasTrabalhados.contains(dia)) {
                faltas++;
            }
        }
        dia = dia.plusDays(1);
    }

    return faltas;
}

// Conta faltas mensais
public static int faltasMensais(int usuarioId) throws Exception {
    Connection con = conexao_BD();

    LocalDate hoje = LocalDate.now();
    LocalDate primeiroDia = hoje.withDayOfMonth(1);

    String sql = "SELECT DISTINCT DATE(entrada) as dia FROM horarios WHERE usuario_id = usuario_id
        AND entrada BETWEEN ps.setObject(2, primeiroDia.atStartOfDay()) AND ps.setObject(3, hoje.atTime(23,59,59))";
    PreparedStatement ps = con.prepareStatement(sql);
    ps.setInt(1, usuarioId);
    ps.setObject(2, primeiroDia.atStartOfDay());
    ps.setObject(3, hoje.atTime(23,59,59));
    ResultSet rs = ps.executeQuery();

    java.util.Set<LocalDate> diasTrabalhados = new java.util.HashSet<>();
    while (rs.next()) {
        diasTrabalhados.add(rs.getDate("dia").toLocalDate());
    }

    int faltas = 0;
    LocalDate dia = primeiroDia;
    while (!dia.isAfter(hoje)) {
        if (dia.getDayOfWeek() != DayOfWeek.SATURDAY && dia.getDayOfWeek() != DayOfWeek.SUNDAY) {
            if (!diasTrabalhados.contains(dia)) {
                faltas++;
            }
        }
        dia = dia.plusDays(1);
    }

    return faltas;
}
             
    }
    }

    // ------------------------- MÉTODOS AUXILIARES -------------------------

    



    //Justificativa de atraso-Elísio

    public static void justifica_atraso(int id) throws Exception{
        Scanner scan=new Scanner(System.in);
                int justificativa = scan.nextInt();
            Connection connect=Conexao.conexao_BD();
                switch (justificativa) {
                    case 1:
                      PreparedStatement pr=connect.prepareStatement("INSERT INTO justificativa(tipo,usuario_id) values(?,?)");
                      pr.setInt(2, id);
                      pr.setString(1,"Falta Injustificada");
                        pr.executeUpdate();
                        System.out.println("Justificativa registrada: Falta Injustificada");                        
                        break;
                    case 2:
                    pr=connect.prepareStatement("INSERT INTO justificativa(tipo,usuario_id) values(?,?)");
                    pr.setInt(2, id);
                      pr.setString(1,"Atestado");
                      pr.executeUpdate();
                        System.out.println("Justificativa registrada: Atestado");                       
                        break;
                    case 3:
                    pr=connect.prepareStatement("INSERT INTO justificativa(tipo,usuario_id) values(?,?)");
                    pr.setInt(2, id);
                    pr.setString(1,"Saída Antecipada");
                    pr.executeUpdate();
                        System.out.println("Justificativa registrada: Saída Antecipada");                       
                        break;
                    case 4:
                     pr=connect.prepareStatement("INSERT INTO justificativa(tipo,usuario_id) values(?,?)");
                    pr.setInt(2, id);
                    pr.setString(1,"Hora Extra");
                    pr.executeUpdate();
                        System.out.println("Justificativa registrada: Hora Extra");                     
                        break;
                    default:
                        System.out.println("Opção inválida.");

                        
       
                }

                scan.close();
    }


    public static void verifica_atraso(int id) throws Exception {
    Connection connect = Conexao.conexao_BD();
    Statement stmt = connect.createStatement();

    // Definindo horário limite de entrada (08:00 da manhã)
    LocalTime horarioLimite = LocalTime.of(8, 0);

    // Consulta entradas do usuário
    ResultSet rs = stmt.executeQuery(
        "SELECT entrada FROM horarios WHERE usuario_id = " + id
    );

    boolean encontrou = false;

    while (rs.next()) {
        LocalDateTime entrada = rs.getTimestamp("entrada").toLocalDateTime();

        if (entrada.toLocalTime().isAfter(horarioLimite)) {
            System.out.println("Atraso registrado no dia: " + entrada.toLocalDate());
            encontrou = true;
        }
    }

    }

    }



