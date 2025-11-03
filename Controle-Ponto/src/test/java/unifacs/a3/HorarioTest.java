package unifacs.a3;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class HorarioTest {

    public static boolean RegistraHora(LocalDateTime entrada, int usuarioId) {
        // Lógica mínima para teste: verifica se parâmetros são válidos
        if (entrada == null || usuarioId <= 0) {
            return false;
        }
        return true;
    }


    // Simulação do método faltasSemanais
    public static int FaltasSemanais(int usuarioId) {
        LocalDate hoje = LocalDate.now();
        LocalDate semanaPassada = hoje.minusDays(7);

        // Mapeando diferentes usuários para diferentes dias trabalhados
        Set<LocalDate> diasTrabalhados = new HashSet<>();
        switch (usuarioId) {
            case 1: // Usuário 1 trabalhou terça e quinta
                diasTrabalhados.add(hoje.minusDays(6)); // terça
                diasTrabalhados.add(hoje.minusDays(4)); // quinta
                break;
            case 2: // Usuário 2 trabalhou segunda, quarta e sexta
                diasTrabalhados.add(hoje.minusDays(7)); // segunda
                diasTrabalhados.add(hoje.minusDays(5)); // quarta
                diasTrabalhados.add(hoje.minusDays(3)); // sexta
                break;
            default: // Usuário sem registro
                break;
        }

        int faltas = 0;
        LocalDate dia = semanaPassada;
        while (!dia.isAfter(hoje)) {
            if (dia.getDayOfWeek() != DayOfWeek.SATURDAY &&
                dia.getDayOfWeek() != DayOfWeek.SUNDAY) {
                if (!diasTrabalhados.contains(dia)) {
                    faltas++;
                }
            }
            dia = dia.plusDays(1);
        }

        return faltas;
    }


      // Simulação do método faltasMensais
    public static int FaltasMensais(int usuarioId) {
        LocalDate hoje = LocalDate.now();
        LocalDate primeiroDia = hoje.withDayOfMonth(1);

        // Mapeando diferentes usuários para diferentes dias trabalhados
        Set<LocalDate> diasTrabalhados = new HashSet<>();
        switch (usuarioId) {
            case 1:
                for (int i = 1; i <= hoje.getDayOfMonth(); i++) {
                    LocalDate diaAtual = primeiroDia.withDayOfMonth(i);
                    if (i % 2 == 0 && diaAtual.getDayOfWeek() != DayOfWeek.SATURDAY &&
                        diaAtual.getDayOfWeek() != DayOfWeek.SUNDAY) {
                        diasTrabalhados.add(diaAtual); // trabalha nos dias pares
                    }
                }
                break;
            case 2:
                for (int i = 1; i <= hoje.getDayOfMonth(); i++) {
                    LocalDate diaAtual = primeiroDia.withDayOfMonth(i);
                    if (i % 3 == 0 && diaAtual.getDayOfWeek() != DayOfWeek.SATURDAY &&
                        diaAtual.getDayOfWeek() != DayOfWeek.SUNDAY) {
                        diasTrabalhados.add(diaAtual); // trabalha a cada 3 dias
                    }
                }
                break;
            default:
                break;
        }

        int faltas = 0;
        LocalDate dia = primeiroDia;
        while (!dia.isAfter(hoje)) {
            if (dia.getDayOfWeek() != DayOfWeek.SATURDAY &&
                dia.getDayOfWeek() != DayOfWeek.SUNDAY) {
                if (!diasTrabalhados.contains(dia)) {
                    faltas++;
                }
            }
            dia = dia.plusDays(1);
        }

        return faltas;
    }
     
     
}
