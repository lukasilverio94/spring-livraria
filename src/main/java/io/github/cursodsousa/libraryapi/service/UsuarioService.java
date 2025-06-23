package io.github.cursodsousa.libraryapi.service;

import io.github.cursodsousa.libraryapi.model.Usuario;
import io.github.cursodsousa.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public void salvar(Usuario usuario) {
        var password = usuario.getPassword();
        usuario.setPassword(encoder.encode(password));
        repository.save(usuario);
    }

    // vamos aqui usar o `loadUserByUsername` da interface do UserDetailsService
    public Usuario obterPorUsername(String username) {
        return repository.findByUsername(username);
    }

}
