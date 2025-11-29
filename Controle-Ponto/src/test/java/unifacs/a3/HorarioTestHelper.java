package unifacs.a3;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

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

    
    public static List<AtrasoDTO> verificaAtraso(int usuarioId) {
        try {
            return horarioBD.verifica_atraso(usuarioId);
        } catch (Exception e) {
            e.printStackTrace();
           
            return new ArrayList<>(); 
        }
    }

   
}