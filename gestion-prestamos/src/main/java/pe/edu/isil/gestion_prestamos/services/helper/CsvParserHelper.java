package pe.edu.isil.gestion_prestamos.services.helper;

import pe.edu.isil.gestion_prestamos.model.Libro;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvParserHelper {

    public List<Libro> parsear(MultipartFile archivo) throws Exception {
        List<Libro> libros = new ArrayList<>();

        BufferedReader reader = new BufferedReader(
            new InputStreamReader(archivo.getInputStream(), StandardCharsets.UTF_8)
        );
        CSVReader csv = new CSVReaderBuilder(reader).withSkipLines(1).build();

        String[] fila;
        while ((fila = csv.readNext()) != null) {
            if (fila.length < 6) continue;
            Libro libro = new Libro();
            libro.setTitulo(fila[0].trim());
            libro.setAutor(fila[1].trim());
            libro.setIsbn(fila[2].trim());
            libro.setCategoria(fila[3].trim());
            libro.setAnio(Integer.parseInt(fila[4].trim()));
            int cantidad = Integer.parseInt(fila[5].trim());
            libro.setCantidad(cantidad);
            libro.setDisponibles(cantidad);
            libros.add(libro);
        }
        return libros;
    }
}