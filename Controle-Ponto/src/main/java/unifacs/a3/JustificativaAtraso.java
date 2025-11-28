package unifacs.a3;

public class JustificativaAtraso {

    private final JustificativaBD justificativaRepository;
    public JustificativaAtraso(JustificativaBD justificativaRepository) {
        this.justificativaRepository = justificativaRepository;
    }
    public String registrarJustificativa(int usuarioId, int idAtraso, int codigoJustificativa) throws Exception {
        String tipo;

        switch (codigoJustificativa) {
            case 1:
                tipo = "Falta Injustificada";
                break;
            case 2:
                tipo = "Atestado";
                break;
            case 3:
                tipo = "Saída Antecipada";
                break;
            case 4:
                tipo = "Hora Extra";
                break;
            default:
                return "Opção inválida.";
        }

        justificativaRepository.salvarJustificativa(usuarioId,idAtraso, tipo);
        return "Justificativa registrada: " + tipo;
    }

}
