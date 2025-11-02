package unifacs.a3;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class HorarioBDTest {

    
    // Teste simples do registro de hora de entrada
    @Test
    void testRegistraHoraSimples() {
        int usuarioId = 1;
        LocalDateTime entrada = LocalDateTime.now();

        // Simula o retorno do método registra_Hora
        boolean registrado = HorarioTest.RegistraHora(entrada, usuarioId);

        // Verificação
        assertTrue(registrado, "O horário deve ser registrado com sucesso");
    }


   


     // Teste simples de cálculo de faltas semanais
    @Test
    void testFaltasSemanaisSimples() {
    int faltas = HorarioTest.FaltasSemanais(1);
        assertTrue(faltas >= 0, "O número de faltas semanais não deve ser negativo.");
        System.out.println("Faltas semanais simuladas para usuário 1: " + faltas);
    }

    // Teste simples de cálculo de faltas mensais
    @Test
    void testFaltasMensaisSimples() {
        int faltas = HorarioTest.FaltasMensais(1);
        assertTrue(faltas >= 0, "O número de faltas mensais não deve ser negativo.");
        System.out.println("Faltas mensais simuladas para usuário 1: " + faltas);
    }

}
