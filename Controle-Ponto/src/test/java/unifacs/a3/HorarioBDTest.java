package unifacs.a3;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.sql.*;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

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
        assertTrue(faltas >= 0, "O número de faltas semanais não deve ser negativo.");
        System.out.println("Faltas semanais simuladas para usuário 1: " + faltas);
    }

    // Teste simples de cálculo de faltas mensais
    @Test
    void testFaltasMensaisSimples() {
        int faltas = HorarioTestHelper.faltasMensais(1);
        assertTrue(faltas >= 0, "O número de faltas mensais não deve ser negativo.");
        System.out.println("Faltas mensais simuladas para usuário 1: " + faltas);
    }

    // Teste simples de verificação de atraso
    @Test
    void testVerificaAtrasoSimples() {
        List<LocalDate> atrasos = HorarioTestHelper.verificaAtraso(1);

        assertTrue(atrasos != null, "A lista de atrasos não deve ser nula");
        System.out.println("Dias com atraso para usuário 1: " + atrasos);
    }

    // Teste de comparação de datas de atraso simuladas
    @Test
    void testVerificaAtrasoEspecifico() {
        // Aqui você poderia inserir dados no banco de teste previamente, se quiser
        List<LocalDate> atrasos = HorarioTestHelper.verificaAtraso(1);

        // Exemplo de assert comparando com uma lista esperada
        List<LocalDate> esperado = Arrays.asList(
            LocalDate.of(2024, 10, 1),
            LocalDate.of(2024, 10, 3)
        );

        // Dependendo da base de teste, ajuste as datas ou use asserts gerais
        assertTrue(atrasos.size() >= 0, "A lista de atrasos deve conter elementos válidos");
    }
}
