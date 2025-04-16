package io.github.cursodsousa.libraryapi.validator;

import io.github.cursodsousa.libraryapi.exceptions.CampoPrecoInvalidoException;
import io.github.cursodsousa.libraryapi.exceptions.RegistroDuplicadoException;
import io.github.cursodsousa.libraryapi.model.Livro;
import io.github.cursodsousa.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    // constante para data a partir desse ano, o preco vai ser obrigatório para validar
    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository repository;

    public void validar(Livro livro) {
        if (existeLivroComIsbn(livro)) {
            throw new RegistroDuplicadoException("ISBN já cadastrado");
        }

        if (isPrecoObrigatorioNull(livro)){
            throw new CampoPrecoInvalidoException(
                    "preco", "Para livros com ano de publicacao a partir de 2020, o preco é obrigatório");
        }
    }

    private boolean isPrecoObrigatorioNull(Livro livro) {
        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
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
