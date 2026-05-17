package pe.edu.isil.gestion_prestamos.controller;

import pe.edu.isil.gestion_prestamos.dto.PrestamoRequestDTO;
import pe.edu.isil.gestion_prestamos.model.Prestamo;
import pe.edu.isil.gestion_prestamos.services.PrestamoService;
import pe.edu.isil.gestion_prestamos.repository.PrestamoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*") 
public class PrestamoController {

    
    @Autowired
    private PrestamoService prestamoService;

    
    @Autowired
    private PrestamoRepository prestamoRepository;

    
    @PostMapping
    public ResponseEntity<?> crearPrestamo(@RequestBody PrestamoRequestDTO dto) {
        try {
            Prestamo nuevoPrestamo = prestamoService.registrarPrestamo(dto);
            return ResponseEntity.ok(nuevoPrestamo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerMisPrestamos(@PathVariable UUID usuarioId) {
        try {
            List<Prestamo> misPrestamos = prestamoRepository.findByUsuarioId(usuarioId);
            return ResponseEntity.ok(misPrestamos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al obtener préstamos: " + e.getMessage());
        }
    }
}