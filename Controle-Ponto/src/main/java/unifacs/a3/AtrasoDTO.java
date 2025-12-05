package unifacs.a3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
       
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
   
        String dataFormatada = dataHora.format(formatter);
        
        String status = (statusJustificativa == null) ? "[PENDENTE]" : "Justificado: " + statusJustificativa;
        
       
        return "ID: " + id + " | Data: " + dataFormatada + " | " + status;
    }

}
