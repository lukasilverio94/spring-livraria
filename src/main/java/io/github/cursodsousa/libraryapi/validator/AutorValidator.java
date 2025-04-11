package io.github.cursodsousa.libraryapi.validator;

import io.github.cursodsousa.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AutorValidator {

    private final AutorRepository repository;

    public AutorValidator(AutorRepository repository) {
        this.repository = repository;
    }

    public void validar(Autor autor) {
        if (existeAutorCadastrado(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }
    }

    // auxiliar method
    private boolean existeAutorCadastrado(Autor autor) {

        Optional<Autor> autorEncontrado = repository.findByNomeAndDataNascimentoAndNacionalidade(
                // dados do autor que queremos cadastrar
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade()
        );

        System.out.println("Autor encontrado: " + autorEncontrado);
        // cadastrar esse autor agora, um novo autor
        if (autor.getId() == null) {
            return autorEncontrado.isPresent(); // ve se tem um autor com os dados passados acima
        }

        return autorEncontrado.filter(value -> !autor.getId().equals(value.getId())).isPresent();

    }
}
