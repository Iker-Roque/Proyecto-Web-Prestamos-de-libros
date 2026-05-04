package pe.edu.isil.gestion_prestamos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ImportResultDTO {
    private int importados;
    private int fallidos;
    private List<String> errores;
}