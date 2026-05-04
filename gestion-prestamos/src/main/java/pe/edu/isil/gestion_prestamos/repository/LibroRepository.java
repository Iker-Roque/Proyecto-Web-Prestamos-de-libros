package pe.edu.isil.gestion_prestamos.repository;


import pe.edu.isil.gestion_prestamos.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findByIsbn(String isbn);
    List<Libro> findByDisponiblesGreaterThan(int cantidad);
}