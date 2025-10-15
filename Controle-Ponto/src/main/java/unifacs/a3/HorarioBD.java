package unifacs.a3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class HorarioBD {



     public static boolean registra_Hora(LocalDateTime entrada, int usuario_id) throws Exception {
        Connection connect =UsuarioBD.conexao_BD();
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
           Connection connect = UsuarioBD.conexao_BD();
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


     public static int faltasSemanais(int usuarioId) throws Exception {
        Connection con = UsuarioBD.conexao_BD();

        LocalDate hoje = LocalDate.now();
        LocalDate semanaPassada = hoje.minusDays(7);

        String sql = "SELECT DISTINCT DATE(entrada) as dia FROM horarios WHERE usuario_id = ? AND entrada BETWEEN ? AND ?";
        PreparedStatement ps = con.prepareStatement(sql);

        ps.setInt(1, usuarioId);
        ps.setObject(2, semanaPassada.atStartOfDay());
        ps.setObject(3, hoje.atTime(23, 59, 59));
        ResultSet rs = ps.executeQuery();

        java.util.Set<LocalDate> diasTrabalhados = new java.util.HashSet<>();
        while (rs.next()) {
            diasTrabalhados.add(rs.getDate("dia").toLocalDate());
        }

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

    public static int faltasMensais(int usuarioId) throws Exception {
        Connection con = UsuarioBD.conexao_BD();

        LocalDate hoje = LocalDate.now();
        LocalDate primeiroDia = hoje.withDayOfMonth(1);

        String sql = "SELECT DISTINCT DATE(entrada) as dia FROM horarios WHERE usuario_id = ? AND entrada BETWEEN ? AND ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, usuarioId);
        ps.setObject(2, primeiroDia.atStartOfDay());
        ps.setObject(3, hoje.atTime(23, 59, 59));
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


    
    public static void verifica_atraso(int id) throws Exception {
        Connection connect = UsuarioBD.conexao_BD();
        Statement stmt = connect.createStatement();

        LocalTime horarioLimite = LocalTime.of(8, 0);

        ResultSet rs = stmt.executeQuery("SELECT entrada FROM horarios WHERE usuario_id = " + id);

        boolean encontrou = false;
        while (rs.next()) {
            LocalDateTime entrada = rs.getTimestamp("entrada").toLocalDateTime();
            if (entrada.toLocalTime().isAfter(horarioLimite)) {
                System.out.println("Atraso registrado no dia: " + entrada.toLocalDate());
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum atraso encontrado.");
        }
    }
}
