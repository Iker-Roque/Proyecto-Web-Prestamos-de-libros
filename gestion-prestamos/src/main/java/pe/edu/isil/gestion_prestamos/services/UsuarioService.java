package pe.edu.isil.gestion_prestamos.services;

import java.util.*;
import pe.edu.isil.gestion_prestamos.dto.LoginDTO;
import pe.edu.isil.gestion_prestamos.model.Usuario;
import pe.edu.isil.gestion_prestamos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Usuario login(LoginDTO dto) {
    Optional<Usuario> usuario = usuarioRepository.findByEmail(dto.getEmail());

    if (usuario.isEmpty()) {
        throw new RuntimeException("Usuario no encontrado");
    }

    if (!usuario.get().getPassword().equals(dto.getPassword())) {
        throw new RuntimeException("Contraseña incorrecta");
    }

    return usuario.get();
}

    public Usuario registrar(Usuario usuario) {
        if(usuario == null) {
            throw new IllegalArgumentException("El Usuario puede ser nulo");
        }
        return usuarioRepository.save(usuario);
    }
}
