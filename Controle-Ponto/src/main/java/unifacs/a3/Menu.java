package unifacs.a3;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    // AGORA RECEBE A CONEXÃO (con) COMO PARÂMETRO
    public static void start(Usuario usuario, Connection con) {
        Scanner scan = new Scanner(System.in);

        // Instancia os objetos de banco UMA VEZ só, usando a conexão recebida
        try {
            UsuarioBD usuarioBD = new UsuarioBD(con);
            HorarioBD horarioBD = new HorarioBD(con);
            JustificativaBD justificativaBD = new JustificativaBD(con);
            FaltaBD faltaBD = new FaltaBD(con);
            boolean continuar = true;

            // O LOOP FICA AQUI DENTRO
            while (continuar) {
                System.out.println("\n--- MENU PRINCIPAL ---");
                System.out.println("Selecione uma opção:");
                System.out.println("1 - Registrar ponto");
                System.out.println("2 - Cadastrar Funcionário");
                System.out.println("3 - Registrar justificativa de atraso");
                System.out.println("4 - Verificar faltas");
                System.out.println("5 - Verificar atrasos");
                System.out.println("6 - Registrar justificativa de falta");
                System.out.println("0 - Sair"); // Opção de sair

                int opcao = scan.nextInt();

                switch (opcao) {
                    case 1:
                        System.out.println("1 - Entrada\n2 - Saída");
                        int resposta = scan.nextInt();
                        if (resposta == 1) {
                            LocalDateTime entrada = LocalDateTime.now();
                            if (horarioBD.registra_Hora(entrada, usuario.getId())) {
                                System.out.println("Entrada registrada!");
                            }
                        } else {
                            horarioBD.registra_Saida(usuario.getId());
                            System.out.println("Saída registrada!");
                        }
                        break;

                    case 2:
                        System.out.println("Nome:");
                        scan.nextLine();
                        String novoNome = scan.nextLine();
                        System.out.println("Email:");
                        String novoEmail = scan.nextLine();
                        System.out.println("Senha (numérica):");
                        int novaSenha = scan.nextInt();

                        Usuario novoUsuario = new Usuario();
                        novoUsuario.setNome(novoNome);
                        novoUsuario.setEmail(novoEmail);
                        novoUsuario.setSenha(novaSenha);

                        usuarioBD.cadastra_Usuario(novoUsuario);
                        break;

                    case 3: 
                        System.out.println("--- SELECIONE UM ATRASO ---");
                       
                        List<AtrasoDTO> listaParaJustificar = horarioBD.verifica_atraso(usuario.getId());

                        for (AtrasoDTO a : listaParaJustificar) {
                            System.out.println(a); // Imprime ID, Data e Status
                        }

                        System.out.println("Digite o ID do atraso:");
                        int idAtraso = scan.nextInt();

                        System.out.println("Motivo: 1-Falta Injustificada, 2-Atestado, 3-Saída Antecipada, 4-Hora Extra");
                        int motivo = scan.nextInt();

                        JustificativaAtraso service = new JustificativaAtraso(justificativaBD);
                        System.out.println(service.registrarJustificativa(usuario.getId(), idAtraso, motivo));
                        break;

                    case 4:
                        System.out.println("Faltas semana: " + horarioBD.faltasSemanais(usuario.getId()));
                        System.out.println("Faltas mês: " + horarioBD.faltasMensais(usuario.getId()));
                        break;

                    case 5: // Apenas Verificar
                        System.out.println("--- SEUS ATRASOS ---");
                        // Reutiliza o mesmo método
                        List<AtrasoDTO> historico = horarioBD.verifica_atraso(usuario.getId());

                        for (AtrasoDTO a : historico) {
                            System.out.println(a);
                        }
                        break;

                    case 6:

                        JustificativaFalta justificativaFalta = new JustificativaFalta(faltaBD);

                        System.out.println("Selecione o código de justificativa para FALTA:");
                        System.out.println("1 - Falecimento");
                        System.out.println("2 - Casamento");
                        System.out.println("3 - Nascimento ou Adoção");
                        System.out.println("4 - Doação de Sangue");
                        System.out.println("5 - Comparecimento em Juízo");

                        int codigo = scan.nextInt();

                        String respostaFalta = justificativaFalta.registrarJustificativa(usuario.getId(), codigo);
                        System.out.println(respostaFalta);
                        break;

                    case 0:
                        continuar = false;
                        System.out.println("Saindo...");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }
            } // Fim do While

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro no menu: " + e.getMessage());
        }

    }
}
