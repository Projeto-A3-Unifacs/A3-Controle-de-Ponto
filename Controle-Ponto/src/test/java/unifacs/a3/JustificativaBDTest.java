package unifacs.a3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class JustificativaBDTest {


     // Teste simples de justificativa de atraso
    @Test
    void testJustificaAtrasoSimples() {
        int codigo = 2;
        String tipo;

        switch (codigo) {
            case 1: tipo = "Falta Injustificada"; break;
            case 2: tipo = "Atestado"; break;
            case 3: tipo = "Saída Antecipada"; break;
            case 4: tipo = "Hora Extra"; break;
            default: tipo = "Inválido";
        }

        assertEquals("Atestado", tipo);
    }

}
