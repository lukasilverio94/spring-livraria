package io.github.cursodsousa.libraryapi.service;

import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import io.github.cursodsousa.libraryapi.repository.LivroRepository;
import io.github.cursodsousa.libraryapi.validator.LivroValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static io.github.cursodsousa.libraryapi.repository.specs.LivroSpecifications.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;
    private  final LivroValidator validator;

    public Livro salvar(Livro livro) {
        validator.validar(livro);
        return repository.save(livro);
    }

    public Optional<Livro> obterPorId(UUID id) {
        return repository.findById(id);
    }

    public void deletar(Livro livro) {
        repository.delete(livro);
    }

    // essa pesquisa aceita filtros
    // Query Params: isbn, titulo, nome autor, genero, ano de publicacao
    public List<Livro> pesquisa(
            String isbn,
            String titulo,
            String nomeAutor,
            GeneroLivro genero,
            Integer anoPublicacao) {

        // select * from livro where isbn = :isbn and nomeAutor = ....
        // depende do que vai querer ser filtrado (aqui ainda ja sabemos quais campos vao ser filtrados)
//        Specification<Livro> specs = Specification
//                .where(LivroSpecifications.isbnEqual(isbn))
//                .and(LivroSpecifications.tituloLike(titulo))
//                .and(LivroSpecifications.generoEqual(genero));


        // E caso nao sabemos os campos, podemos fazer o seguinte
        // Aqui vai filtrar baseado no que foi passado mas ainda nao sabemos
        // Usuario poderia filtrar por isbn, titulo, nomeAutor, genero, dataPublicacao
        // select * from livro where 0 = 0
        Specification<Livro> specs =
                Specification.where((root, query, cb) -> cb.conjunction()
        );

        if (isbn != null){
            // query = query and isbn = :isbn
            specs = specs.and(isbnEqual(isbn));
        }

        if (titulo != null){
            specs = specs.and(tituloLike(titulo));
        }

        if (nomeAutor != null) {
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        if (genero != null){
            specs = specs.and(generoEqual(genero));
        }

        if (anoPublicacao != null) {
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        return repository.findAll(specs);
    }

    public void atualizar(Livro livro) {
        if (livro.getId() == null){
            throw new IllegalArgumentException("Para atualizar, é necessário que o livro já esteja salvo no database");
        }
        validator.validar(livro);
        repository.save(livro);
    }
}
