package io.github.cursodsousa.libraryapi.repository.specs;

import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecifications {

    // nome das specifications devem ser "nomeDaPropriedade(do objeto, nao do banco) + métodoSQL(Equal, Like, etc)
    // isbnEqual, tituloLike, generoEqual, etc.
    public static Specification<Livro> isbnEqual(String isbn) {
        return
                (root,
                 query,
                 criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), isbn);
    }

    // Aqui queremos o 'like', porque queremos filtrar mesmo que o título nao esteja completo
    // O Mar e o Velho -> pesquisa("mar") já retornaria o Livro.
    public static Specification<Livro> tituloLike(String titulo) {
        // upper(livro.titulo) like (%:param%)
        return (root,
                query,
                cb) ->
                cb.like(cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%"); // cb.upper pra ignorar CaSe
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero) {
        return (root, query, cb) ->
                cb.equal(root.get("genero"), genero);
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao) {
        // nao temos anoPublicacao, entao temos que pegar o ano da dataPublicacao (que é da entidade)
        // to_char(data_publicacao, 'YYYY') = :anoPublicacao <----- funcao postgresql
        return (root, query, cb) ->
                cb.equal(
                        cb.function("to_char", String.class, root.get("dataPublicacao"), cb.literal("YYYY")),
                        anoPublicacao.toString());
    }

    /*  select l.*
        from livro as l
        join autor as a on a.id = l.id_autor
        where upper(a.nome) like upper('%param%')
     */
    public static Specification<Livro> nomeAutorLike(String nome) {
        return (root, query, cb) -> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);
            return cb.like(cb.upper(joinAutor.get("nome")), "%" + nome.toUpperCase() + "%");

            // AQUI SEMPRE VAI FAZER O INNER JOIN POR PADRÃO
            // Entao pra ficar mais flexivel vamos deixar este comentado e usar o outro acima
//            return cb.like( cb.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%");
        };
    }
}