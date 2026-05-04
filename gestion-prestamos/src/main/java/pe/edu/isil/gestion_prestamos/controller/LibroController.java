package pe.edu.isil.gestion_prestamos.controller;

import pe.edu.isil.gestion_prestamos.dto.ImportResultDTO;
import pe.edu.isil.gestion_prestamos.model.Libro;
import pe.edu.isil.gestion_prestamos.services.LibroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LibroController {

    private final LibroService libroService;

    @PostMapping("/importar")
    public ResponseEntity<ImportResultDTO> importar(
            @RequestParam("archivo") MultipartFile archivo) {
        return ResponseEntity.ok(libroService.importarDesdeArchivo(archivo));
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Libro>> disponibles() {
        return ResponseEntity.ok(libroService.listarDisponibles());
    }

    @GetMapping
    public ResponseEntity<List<Libro>> todos() {
        return ResponseEntity.ok(libroService.listarTodos());
    }
}