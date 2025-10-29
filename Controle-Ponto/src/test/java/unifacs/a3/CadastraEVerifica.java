package unifacs.a3;


public class CadastraEVerifica {

    


     public static Usuario verificaUser(Usuario usuario, UsuarioBD usuarioBD) {
        // Simulando um "usuário válido"
       
        try {   
           
            return usuarioBD.verificaUser(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


       
    }


    // Simula o comportamento do método cadastra_Usuario (sem BD)
    public static boolean simulaCadastro(Usuario usuario)  {
        try{
        UsuarioBD usuarioBD = new UsuarioBD();
        if (usuario.getNome() == null || usuario.getNome().isEmpty()) return false;
        if (usuario.getSenha()<= 0) return false;

        // Simulação de "inserção" bem-sucedida
        usuarioBD.cadastra_Usuario(usuario);
        return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
