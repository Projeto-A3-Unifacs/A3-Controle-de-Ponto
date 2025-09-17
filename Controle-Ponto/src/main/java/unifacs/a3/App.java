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
                Resultado r1 = verificaUser(nome_user, senha_user);
         if (r1.user_encontrado()) {
        // Menu inicial
        System.out.println("Selecione uma opção:\n1-Registrar ponto\n2-Cadastrar Funcionario\n3-Registrar justificativa de atraso\n4-Contabilização de faltas");
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
                        boolean registrado = registra_Hora(entrada, r1.id_user());

                        if (registrado) {
                            System.out.println("Sua entrada foi registrada com sucesso");
                        }

                    // ----------------- REGISTRA SAÍDA -----------------
                    } else {
                        Connection connect = conexao_BD();
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
                
                    break;

            // ------------------------- CASE 2: Cadastrar funcionário -------------------------
            case 2:
                System.out.println("Digite o nome do funcionário:");
                scan.nextLine();
                String nome = scan.nextLine();

                System.out.println("Crie uma nova senha:");
                int senha = scan.nextInt();

                try (Connection conectando = conexao_BD()) {
                    PreparedStatement ps = conectando.prepareStatement(
                        "INSERT INTO usuario (nome, senha) VALUES (?, ?)"
                    );
                    ps.setString(1, nome);
                    ps.setInt(2, senha);
                    ps.executeUpdate();
                }
                break;

            // ------------------------- CASE 3: Justificar atraso -------------------------
            case 3:
               System.out.println() ;
               break;
            

        }

        
        }else{
            System.out.println("Usuário não cadastrado");
            System.out.println("1-Recuperar senha\n2-Sair");
            alternativa=scan.nextInt();
            if(alternativa==1){
                System.out.println("digite seu email:");
                recupera_senha();

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

    // Faz a conexão com o banco de dados PostgreSQL
    public static Connection conexao_BD() {
        String url = "jdbc:postgresql://aws-1-us-east-1.pooler.supabase.com:6543/postgres?sslmode=require";
        String user = "postgres.iczuamsiqqdvifexshbf";
        String password = "miniProject25@";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conexão bem-sucedida com o PostgreSQL!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // Verifica se o usuário existe no banco
    public static Resultado verificaUser(String nome_user, int senha_user) throws Exception {
        Connection connect = conexao_BD();
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

    // Registra a hora de entrada no banco
    public static boolean registra_Hora(LocalDateTime entrada, int usuario_id) throws Exception {
        Connection connect = conexao_BD();
        boolean horario_registrado = false;

        PreparedStatement ps = connect.prepareStatement(
            "INSERT INTO horarios (usuario_id, entrada) VALUES (?, ?)"
        );
        ps.setInt(1, usuario_id);
        ps.setObject(2, entrada);
        ps.executeUpdate();

        return horario_registrado = true;
    }
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
    }
}
