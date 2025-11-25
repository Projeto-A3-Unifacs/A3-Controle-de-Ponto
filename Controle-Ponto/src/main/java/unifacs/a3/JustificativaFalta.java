package unifacs.a3;

public class JustificativaFalta {

    private final FaltaBD justificativaRepository;

    public JustificativaFalta(FaltaBD justificativaRepository) {
        this.justificativaRepository = java.util.Objects.requireNonNull(justificativaRepository);
    }

    public String registrarJustificativa(int usuarioId, int codigoJustificativa) throws Exception {
        String tipo;

        switch (codigoJustificativa) {
            case 1:
                tipo = "Falecimento";
                break;
            case 2:
                tipo = "Casamento";
                break;
            case 3:
                tipo = "Nascimento ou Adoção";
                break;
            case 4:
                tipo = "Doação de Sangue";
                break;
            case 5:
                tipo = "Comparecimento em Juízo";
                break;
            default:
                return "Opção inválida";
        }

        justificativaRepository.registra_faltaBD(usuarioId, tipo);
        return "Justificativa registrada: " + tipo;
    }
}
