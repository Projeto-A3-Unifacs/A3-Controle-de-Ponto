package unifacs.a3;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Menu {

  public static void start(Usuario usuario) throws Exception {
    Scanner scan = new Scanner(System.in);
    
            // Menu inicial
            System.out.println("Selecione uma opção:");
            System.out.println("1 - Registrar ponto");
            System.out.println("2 - Cadastrar Funcionário");
            System.out.println("3 - Registrar justificativa de atraso");
            System.out.println("4 - Verificar faltas");
            System.out.println("5 - Verificar atrasos");
            int opcao = scan.nextInt();

            switch (opcao) {

                // ------------------------- CASE 1: Registrar ponto -------------------------
                case 1:
                    System.out.println("1 - Entrada\n2 - Saída");
                    int resposta = scan.nextInt();

                    if (resposta == 1) {
                        LocalDateTime entrada = LocalDateTime.now();
                        boolean registrado = HorarioBD.registra_Hora(entrada, usuario.getId());

                        if (registrado) {
                            System.out.println("Sua entrada foi registrada com sucesso");
                        }
                    } else {
                        HorarioBD.registra_Saida();
                        System.out.println("Sua saída foi registrada com sucesso");
                    }
                    break;

                // ------------------------- CASE 2: Cadastrar funcionário -------------------------
                case 2:
                    System.out.println("Digite o nome do funcionário:");
                    scan.nextLine();
                    String novoNome = scan.nextLine();

                    System.out.println("Crie uma nova senha:");
                    int novaSenha = scan.nextInt();

                    Usuario novoUsuario = new Usuario();
                    novoUsuario.setNome(novoNome);
                    novoUsuario.setSenha(novaSenha);
                    UsuarioBD.cadastra_Usuario(novoUsuario.getNome(), novoUsuario.getSenha());
                    break;

                // ------------------------- CASE 3: Justificar atraso -------------------------
                case 3:
                    System.out.println("Selecione o código de justificativa:");
                    System.out.println("1 - Falta Injustificada");
                    System.out.println("2 - Atestado");
                    System.out.println("3 - Saída Antecipada");
                    System.out.println("4 - Hora Extra");

                    JustificativaBD.justifica_atraso(usuario.getId());
                    break;

                // ------------------------- CASE 4: Verificar faltas -------------------------
                case 4:
                    System.out.println("Faltas na semana: " + HorarioBD.faltasSemanais(usuario.getId()));
                    System.out.println("Faltas no mês: " + HorarioBD.faltasMensais(usuario.getId()));
                    break;

                // ------------------------- CASE 5: Verificar Atrasos -------------------------
                case 5:
                    System.out.println("Verificando atrasos...");
                    HorarioBD.verifica_atraso(usuario.getId());
                    break;
            }
  }



}
