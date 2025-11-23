package unifacs.a3;

import java.sql.Connection;

public class CadastraEVerifica {

    private static Connection con;

    static {
        try {
            // Tenta conectar, mas não trava se falhar (útil para testes sem banco)
            con = ConnectionManager.getConnection(); 
        } catch (Exception e) {
            System.out.println("Aviso: Não foi possível estabelecer conexão estática.");
        }
    }

    // CORREÇÃO: Usar o usuarioBD que foi passado, se ele existir
    public static Usuario verificaUser(Usuario usuario, UsuarioBD usuarioBD) {
        try {
            // Se veio nulo (uso em produção), cria o real. 
            // Se veio preenchido (uso em teste), usa o mock.
            if (usuarioBD == null) {
                usuarioBD = new UsuarioBD(con);
            }
            return usuarioBD.verificaUser(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // CORREÇÃO: Adicionamos a sobrecarga para aceitar o Mock no teste
    public static boolean simulaCadastro(Usuario usuario, UsuarioBD usuarioBD) {
        try {
            if (usuario.getNome() == null || usuario.getNome().isEmpty()) return false;
            if (usuario.getSenha() <= 0) return false;

            // Se veio nulo, cria o real
            if (usuarioBD == null) {
                usuarioBD = new UsuarioBD(con);
            }
            
            usuarioBD.cadastra_Usuario(usuario);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Sobrecarga para manter compatibilidade com quem chama sem passar o BD
    public static boolean simulaCadastro(Usuario usuario) {
        return simulaCadastro(usuario, null);
    }

    public static void fecharConexao() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
