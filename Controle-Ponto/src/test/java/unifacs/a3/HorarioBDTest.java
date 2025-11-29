package unifacs.a3;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

public class HorarioBDTest {

    // Teste simples do registro de hora de entrada
    @Test
    void testRegistraHoraSimples() {
        int usuarioId = 1;
        LocalDateTime entrada = LocalDateTime.now();

        boolean registrado = HorarioTestHelper.registraHora(entrada, usuarioId);

        assertTrue(registrado, "O horário deve ser registrado com sucesso");
    }

    // Teste simples de cálculo de faltas semanais
    @Test
    void testFaltasSemanaisSimples() {
        int faltas = HorarioTestHelper.faltasSemanais(1);
        
        // Verifica se é um número válido (não pode ser negativo)
        assertTrue(faltas >= 0, "O número de faltas semanais não deve ser negativo.");
    }

    // Teste simples de cálculo de faltas mensais
    @Test
    void testFaltasMensaisSimples() {
        int faltas = HorarioTestHelper.faltasMensais(1);
        
        assertTrue(faltas >= 0, "O número de faltas mensais não deve ser negativo.");
     
    }

    // Teste simples de verificação de atrasos
    @Test
    void testVerificaAtrasoSimples() {
        List<AtrasoDTO> atrasos = HorarioTestHelper.verificaAtraso(1);
        
        assertNotNull(atrasos, "A lista de atrasos não deve ser nula.");
    }
}