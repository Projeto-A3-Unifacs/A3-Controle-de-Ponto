package unifacs.a3;

import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {

        int alternativa = 0;
        Scanner scan = new Scanner(System.in);

        // Acesso ao Sistema
        Usuario user= new Usuario();
        System.out.println("Digite seu nome:");
        String nome = scan.nextLine();
         user.setNome(nome);
        System.out.println("Digite sua senha:");
        int senha = scan.nextInt();
        user.setSenha(senha);
        // Verifica se o usuário existe no BD
        UsuarioBD usuarioBD = new UsuarioBD();
        Usuario usuario =usuarioBD.verificaUser(user);
   //INICIO MENU
        if (usuario != null && usuario.getId() > 0) {
       Menu.start(usuario);

        } else {
            System.out.println("Usuário não cadastrado");
            System.out.println("1 - Recuperar senha\n2 - Sair");
            alternativa = scan.nextInt();
            if (alternativa == 1) {
                System.out.println("Digite seu email:");
                scan.nextLine();
                String email= scan.nextLine();
               
               System.out.println(usuarioBD.recupera_senha(email));
            }
        }

        scan.close();
    }

  
}
