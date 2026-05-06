package pe.edu.isil.gestion_prestamos.services.helper;

import pe.edu.isil.gestion_prestamos.model.Libro;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelParserHelper {

    public List<Libro> parsear(MultipartFile archivo) throws Exception {
        List<Libro> libros = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(archivo.getInputStream())) {
            Sheet hoja = workbook.getSheetAt(0);

            for (int i = 1; i <= hoja.getLastRowNum(); i++) {
                Row fila = hoja.getRow(i);
                if (fila == null)
                    continue;
                if (getCellValue(fila, 0).isEmpty())
                    continue;

                Libro libro = new Libro();
                libro.setTitulo(getCellValue(fila, 0));
                libro.setAutor(getCellValue(fila, 1));
                libro.setIsbn(getCellValue(fila, 2));
                libro.setCategoria(getCellValue(fila, 3));
                libro.setAnio((int) fila.getCell(4).getNumericCellValue());
                int cantidad = (int) fila.getCell(5).getNumericCellValue();
                libro.setCantidad(cantidad);
                libro.setDisponibles(cantidad);
                libros.add(libro);
            }
        } 

        return libros;
    }

    private String getCellValue(Row fila, int col) {
        Cell cell = fila.getCell(col);
        if (cell == null)
            return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            default -> "";
        };
    }
}