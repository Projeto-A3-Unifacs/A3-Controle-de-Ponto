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
import java.util.ArrayList;
import java.util.List;

public class HorarioBD {

  private Connection connect;

   public HorarioBD(Connection connect) throws Exception {
        this.connect = connect;
    }

     public  boolean registra_Hora(LocalDateTime entrada, int usuario_id) throws Exception {
       
        boolean horario_registrado = false;

        PreparedStatement ps = connect.prepareStatement(
            "INSERT INTO horarios (usuario_id, entrada) VALUES (?, ?)"
        );
        ps.setInt(1, usuario_id);
        ps.setObject(2, entrada);
        ps.executeUpdate();

        return horario_registrado = true;
    }


  // Registra ponto de saída para um usuário específico
public  void registra_Saida(int usuarioId) throws Exception {
    
    
   
        // Busca o último registro sem saída para esse usuário
        String sqlBusca = "SELECT id FROM horarios WHERE saida IS NULL AND usuario_id = ? ORDER BY id DESC LIMIT 1";
        PreparedStatement stmtBusca = connect.prepareStatement(sqlBusca);
        stmtBusca.setInt(1, usuarioId);
        
        ResultSet rs = stmtBusca.executeQuery();

        if (rs.next()) {
            int id_buscado = rs.getInt("id");

            // Atualiza a saída no registro encontrado
            String sqlAtualiza = "UPDATE horarios SET saida = ? WHERE id = ?";
            PreparedStatement ps = connect.prepareStatement(sqlAtualiza);
            ps.setObject(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(2, id_buscado);
            ps.executeUpdate();

            ps.close();
        } else {
            System.out.println("Nenhuma entrada aberta encontrada para o usuário.");
        }

        rs.close();
        stmtBusca.close();
   
    
}


     public  int faltasSemanais(int usuarioId) throws Exception {
       

        LocalDate hoje = LocalDate.now();
        LocalDate semanaPassada = hoje.minusDays(7);

        String sql = "SELECT DISTINCT DATE(entrada) as dia FROM horarios WHERE usuario_id = ? AND entrada BETWEEN ? AND ?";
        PreparedStatement ps = connect.prepareStatement(sql);

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

    public  int faltasMensais(int usuarioId) throws Exception {
      

        LocalDate hoje = LocalDate.now();
        LocalDate primeiroDia = hoje.withDayOfMonth(1);

        String sql = "SELECT DISTINCT DATE(entrada) as dia FROM horarios WHERE usuario_id = ? AND entrada BETWEEN ? AND ?";
        PreparedStatement ps = connect.prepareStatement(sql);
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


    
     public  List<LocalDate> verifica_atraso(int id) throws Exception {
        List<LocalDateTime> entradas = buscarEntradasPorUsuario(id);
        List<LocalDate> atrasos = new ArrayList<>();

        LocalTime horarioLimite = LocalTime.of(8, 0);

        for (LocalDateTime entrada : entradas) {
            if (entrada.toLocalTime().isAfter(horarioLimite)) {
                atrasos.add(entrada.toLocalDate());
            }
        }

        return atrasos;
    }

    // Método auxiliar: consulta o BD (ou pode ser substituído por dados falsos no teste)
    public  List<LocalDateTime> buscarEntradasPorUsuario(int id) throws Exception {
        
        Statement stmt = connect.createStatement();

        ResultSet rs = stmt.executeQuery(
            "SELECT entrada FROM horarios WHERE usuario_id = " + id
        );

        List<LocalDateTime> entradas = new ArrayList<>();
        while (rs.next()) {
            entradas.add(rs.getTimestamp("entrada").toLocalDateTime());
        }

        rs.close();
        stmt.close();
       

        return entradas;
    }
}
