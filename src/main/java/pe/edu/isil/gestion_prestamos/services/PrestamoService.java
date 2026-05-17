package pe.edu.isil.gestion_prestamos.services;

import pe.edu.isil.gestion_prestamos.dto.PrestamoRequestDTO;
import pe.edu.isil.gestion_prestamos.model.Prestamo;
import pe.edu.isil.gestion_prestamos.model.Libro;
import pe.edu.isil.gestion_prestamos.repository.PrestamoRepository;
import pe.edu.isil.gestion_prestamos.repository.LibroRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Transactional
    public Prestamo registrarPrestamo(PrestamoRequestDTO dto) {
        

        // 1. Validar existencia del libro
        @SuppressWarnings("null")
        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new RuntimeException("El libro solicitado no existe."));

        // 2. Verificar disponibilidad de stock
        if (libro.getDisponibles() <= 0) {
            throw new RuntimeException("No hay ejemplares disponibles para préstamo.");
        }

        // 3. Modificar y guardar nuevo stock del libro
        libro.setDisponibles(libro.getDisponibles() - 1);
        libroRepository.save(libro);

        // 4. Registrar y persistir la transacción de préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setLibroId(dto.getLibroId());
        prestamo.setUsuarioId(dto.getUsuarioId());

        return prestamoRepository.save(prestamo);
    }
}
