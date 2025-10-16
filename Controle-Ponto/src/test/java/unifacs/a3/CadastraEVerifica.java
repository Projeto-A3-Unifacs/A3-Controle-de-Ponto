package unifacs.a3;

public class CadastraEVerifica {





     public static Usuario verificaUser(String nome, int senha) {
        // Simulando um "usuário válido"
        if (nome.equals("TesteUser") && senha == 1234) {
            Usuario u = new Usuario();
            u.setId(1);
            u.setNome(nome);
            u.setEmail("teste@email.com");
            u.setSenha(senha);
            return u;
        }
        // Caso contrário, retorna null (usuário não encontrado)
        return null;
    }


    // Simula o comportamento do método cadastra_Usuario (sem BD)
    public static boolean simulaCadastro(String nome, int senha) {
        if (nome == null || nome.isEmpty()) return false;
        if (senha <= 0) return false;

        // Simulação de "inserção" bem-sucedida
        System.out.println("Usuário cadastrado: " + nome);
        return true;
    }

}
