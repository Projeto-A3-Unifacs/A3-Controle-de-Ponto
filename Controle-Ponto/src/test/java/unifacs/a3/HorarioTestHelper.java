package unifacs.a3;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HorarioTestHelper {

     private static Connection conexao;
    private static HorarioBD horarioBD;

    // Inicializa a conexão e o DAO apenas uma vez
    static {
        try {
            conexao = ConnectionManager.getConnection();
            horarioBD = new HorarioBD(conexao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean registraHora(LocalDateTime entrada, int usuarioId) {
        try {
            return horarioBD.registra_Hora(entrada, usuarioId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int faltasSemanais(int usuarioId) {
        try {
            return horarioBD.faltasSemanais(usuarioId);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int faltasMensais(int usuarioId) {
        try {
            return horarioBD.faltasMensais(usuarioId);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static List<LocalDate> verificaAtraso(int usuarioId) {
        try {
            return horarioBD.verifica_atraso(usuarioId);
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    // Se precisar de acesso direto ao DAO
    public static HorarioBD getHorarioBD() {
        return horarioBD;
    }
     
     
}
