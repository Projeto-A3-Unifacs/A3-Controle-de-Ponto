package unifacs.a3;

import java.util.Scanner;

public class App {

    public static void main(String[] args) throws Exception {

        int alternativa = 0;
        Scanner scan = new Scanner(System.in);

        // Acesso ao Sistema
        System.out.println("Digite seu nome:");
        String nome = scan.nextLine();

        System.out.println("Digite sua senha:");
        int senha = scan.nextInt();

        // Verifica se o usuário existe no BD
        Usuario usuario = UsuarioBD.verificaUser(nome, senha);
   //INICIO MENU
        if (usuario != null && usuario.getId() > 0) {
       Menu.start(usuario);

        } else {
            System.out.println("Usuário não cadastrado");
            System.out.println("1 - Recuperar senha\n2 - Sair");
            alternativa = scan.nextInt();
            if (alternativa == 1) {
                System.out.println("Digite seu email:");
                UsuarioBD.recupera_senha();
            }
        }

        scan.close();
    }

  
}
