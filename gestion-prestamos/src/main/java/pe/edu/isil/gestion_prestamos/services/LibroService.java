package pe.edu.isil.gestion_prestamos.services;

import pe.edu.isil.gestion_prestamos.dto.ImportResultDTO;
import pe.edu.isil.gestion_prestamos.model.Libro;
import pe.edu.isil.gestion_prestamos.repository.LibroRepository;
import pe.edu.isil.gestion_prestamos.services.helper.CsvParserHelper;
import pe.edu.isil.gestion_prestamos.services.helper.ExcelParserHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LibroService {

    private final LibroRepository libroRepository;
    private final CsvParserHelper csvParser;
    private final ExcelParserHelper excelParser;

    public ImportResultDTO importarDesdeArchivo(MultipartFile archivo) {
        List<String> errores = new ArrayList<>();
        List<Libro> librosValidos = new ArrayList<>();

        try {
            String originalFilename = archivo.getOriginalFilename();
            Objects.requireNonNull(originalFilename, "El nombre del archivo no puede ser nulo");

            String nombre = originalFilename.toLowerCase();
            List<Libro> parseados = nombre.endsWith(".csv")
                ? csvParser.parsear(archivo)
                : excelParser.parsear(archivo);

            for (int i = 0; i < parseados.size(); i++) {
                Libro libro = parseados.get(i);
                if (libroRepository.findByIsbn(libro.getIsbn()).isPresent()) {
                    errores.add("Fila " + (i + 2) + ": ISBN " + libro.getIsbn() + " ya existe");
                    continue;
                }
                librosValidos.add(libro);
            }

            libroRepository.saveAll(librosValidos);

        } catch (Exception e) {
            errores.add("Error: " + e.getMessage());
        }

        return new ImportResultDTO(librosValidos.size(), errores.size(), errores);
    }

    public List<Libro> listarDisponibles() {
        return libroRepository.findByDisponiblesGreaterThan(0);
    }

    public List<Libro> listarTodos() {
        return libroRepository.findAll();
    }
}