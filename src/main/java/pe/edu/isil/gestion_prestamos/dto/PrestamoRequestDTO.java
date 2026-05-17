package pe.edu.isil.gestion_prestamos.dto;

import java.util.UUID; 

public class PrestamoRequestDTO {
    private Long libroId;
    
    // CAMBIADO DE String A UUID
    private UUID usuarioId;

    // Getters y Setters
    public Long getLibroId() { return libroId; }
    public void setLibroId(Long libroId) { this.libroId = libroId; }

    public UUID getUsuarioId() { return usuarioId; }
    public void setUsuarioId(UUID usuarioId) { this.usuarioId = usuarioId; }
}