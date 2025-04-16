package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */
public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {


    // Query Method -> usar findByNomeDaPropriedade em CaseAssim.
    // select * from livro where id_autor = ?
    List<Livro> findByAutor(Autor autor);

    // select * from livro where titulo = ?
    List<Livro> findByTitulo(String titulo);

    // select * from livro where isbn = ?;
    Optional<Livro> findByIsbn(String isbn);

    // select * from livro where titulo = ? AND preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // select * from livro where titulo = ? OR isbn = ?
    List<Livro> findByTituloOrIsbn(String titulo, String isbn);

    // select * from livro where data_publicacao between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    List<Livro> findByTituloEndingWithIgnoreCase(String titulo);

    // ########## JPQL @Query ###################
    // JPQL -> Referencia as entidades e as propriedades
    @Query("select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarLivrosOrdenadoPorTituloAndPreco();

    /* JOIN IN JPQL
    select a.*
    from livro l
    join autor a on a.id = l.id_autor
     */
    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    /* select distinct l.* from livro l */
    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    /* Uma query maior do que uma linha (usar 3 aspas duplas, pra abrir e fechar) */
    @Query("""
                   select l.genero
                   from Livro l
                   join l.autor a
                   where a.nacionalidade = 'Brasileira'
                   order by l.genero
            """)
    List<String> listarGenerosAutoresBrasileiros();

    /* ****************************************************************************/
    /* @Query com Parametros */

    /* Named Parameters -> Parametros nomeados */
    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGeneroNamedParameters(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomePropriedade
    );

    /* Positional Parameteres*/
    @Query("select l from Livro l where l.genero = ?1 order by ?2")
    List<Livro> findByGeneroPositionalParameters(GeneroLivro generoLivro, String nomePropriedade);

    /* ****************************************************************************/

    /* Operacoes Delete e Update utilizando Queries */
    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate novaData);
    /* ****************************************************************************/

    // Checar se existe livro com esse autor
    boolean existsByAutor(Autor autor);
}
