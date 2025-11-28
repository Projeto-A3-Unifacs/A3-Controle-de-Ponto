package unifacs.a3;

import java.time.LocalDateTime;

public class AtrasoDTO {

    private int id;
    private LocalDateTime dataHora;
    private String statusJustificativa; // Pode ser null ou o motivo

    public AtrasoDTO(int id, LocalDateTime dataHora, String statusJustificativa) {
        this.id = id;
        this.dataHora = dataHora;
        this.statusJustificativa = statusJustificativa;
    }

    // Getters
    public int getId() { return id; }
    public LocalDateTime getDataHora() { return dataHora; }
    public String getStatusJustificativa() { return statusJustificativa; }
    
    @Override
    public String toString() {
        String status = (statusJustificativa == null) ? "[PENDENTE]" : "Justificado: " + statusJustificativa;
        return "ID: " + id + " | Data: " + dataHora + " | " + status;
    }

}
