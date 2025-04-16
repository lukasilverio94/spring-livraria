package io.github.cursodsousa.libraryapi.validator;

import io.github.cursodsousa.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.cursodsousa.libraryapi.model.Livro;
import io.github.cursodsousa.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final LivroRepository repository;

    public void validar(Livro livro) {
        if (existeLivroComIsbn(livro)) {
            throw new RegistroDuplicadoException("ISBN já cadastrado");
        }
    }

    private boolean existeLivroComIsbn(Livro livro) {
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());

        // se estou cadastrando o livro pela primeira vez (nao atualizando)
        if (livro.getId() == null) {
            return livroEncontrado.isPresent();
        }

        // caso esteja atualizando o livro
        // se livro encontrado tiver o mesmo id que o livro que estamos tentando cadastrar, retornar true
        // filtrar do optional que nao tenha o mesmo id do livro como param
        return livroEncontrado
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }
}
