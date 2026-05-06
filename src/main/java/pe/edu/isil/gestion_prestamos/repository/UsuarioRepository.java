package pe.edu.isil.gestion_prestamos.repository;

import java.util.Optional;

import pe.edu.isil.gestion_prestamos.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
