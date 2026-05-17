package pe.edu.isil.gestion_prestamos.controller;

import pe.edu.isil.gestion_prestamos.dto.PrestamoRequestDTO;
import pe.edu.isil.gestion_prestamos.model.Prestamo;
import pe.edu.isil.gestion_prestamos.services.PrestamoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*") 
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<?> crearPrestamo(@RequestBody PrestamoRequestDTO dto) {
        try {
            Prestamo nuevoPrestamo = prestamoService.registrarPrestamo(dto);
            return ResponseEntity.ok(nuevoPrestamo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
