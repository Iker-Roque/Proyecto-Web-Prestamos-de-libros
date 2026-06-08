package pe.edu.isil.gestion_prestamos.services;

import pe.edu.isil.gestion_prestamos.dto.PrestamoRequestDTO;
import pe.edu.isil.gestion_prestamos.model.Prestamo;
import pe.edu.isil.gestion_prestamos.model.Libro;
import pe.edu.isil.gestion_prestamos.repository.PrestamoRepository;
import pe.edu.isil.gestion_prestamos.repository.LibroRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime; //localDateTime para manejar fechas de préstamo y vencimiento
import java.util.Objects;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private LibroRepository libroRepository;

    @Transactional
    public Prestamo registrarPrestamo(PrestamoRequestDTO dto) {
        // Validar existencia del libro
        @SuppressWarnings("null")
        Libro libro = libroRepository.findById(dto.getLibroId())
                .orElseThrow(() -> new RuntimeException("El libro solicitado no existe."));

        // Verificar disponibilidad de stock
        if (libro.getDisponibles() <= 0) {
            throw new RuntimeException("No hay ejemplares disponibles para préstamo.");
        }

        // Modificar y guardar nuevo stock del libro
        libro.setDisponibles(libro.getDisponibles() - 1);
        libroRepository.save(libro);

        // Registrar y persistir la transacción de préstamo
        Prestamo prestamo = new Prestamo();
        prestamo.setLibroId(dto.getLibroId());
        prestamo.setUsuarioId(dto.getUsuarioId());
        prestamo.setDniUsuario(dto.getDniUsuario());

        // (Opcional DB no tiene un default) prestamo.setEstado("solicitado");

        return prestamoRepository.save(prestamo);
    }

    // --- NUEVOS MÉTODOS PARA EL ADMINISTRADOR ---

    @Transactional
    public Prestamo aprobarPrestamo(long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        prestamo.setEstado("prestado");
        prestamo.setFechaVencimiento(LocalDateTime.now().plusDays(7)); // Configura el límite

        return prestamoRepository.save(prestamo);
    }

    @Transactional
    public Prestamo rechazarPrestamo(long id) {
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        prestamo.setEstado("rechazado");

        // Devolver el stock al catálogo
        Libro libro = libroRepository.findById(
            Objects.requireNonNull(prestamo.getLibroId(), "El libro ID no puede ser null"))
            .orElseThrow(() -> new RuntimeException("Libro no encontrado para restaurar stock"));

        libro.setDisponibles(libro.getDisponibles() + 1);
        libroRepository.save(libro);

        return prestamoRepository.save(prestamo);
    }
}