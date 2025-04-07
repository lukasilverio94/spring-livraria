package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest() {
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.CIENCIA);
        livro.setTitulo("Ciencia Aplicada");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = autorRepository
                .findById(UUID.fromString("9f7731c8-5fd3-4933-9d17-5095d44a71e6"))
                .orElse(null);

//        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void salvarAutorELivroTest() {
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("A volta dos que nao foram");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("Caroline");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        autorRepository.save(autor);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void salvarCascadeTest() {
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        Autor autor = new Autor();
        autor.setNome("João");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        livro.setAutor(autor);

        livroRepository.save(livro);
    }

    @Test
    void atualizarAutorDoLivro() {
        UUID id = UUID.fromString("2230ee89-d961-4d24-a073-df3315ee7202");
        var livroParaAtualizar = livroRepository.findById(id).orElse(null);

        UUID idAutor = UUID.fromString("358b6d41-9b48-40a5-a8ac-24e40d1bf873");
        Autor caroline = autorRepository.findById(idAutor).orElse(null);
        livroParaAtualizar.setAutor(caroline);

        livroRepository.save(livroParaAtualizar);
    }


    @Test
    void deletar() {
        UUID id = UUID.fromString("2230ee89-d961-4d24-a073-df3315ee7202");
        livroRepository.deleteById(id);
    }

    @Test
    void deletarCascade() {
        UUID id = UUID.fromString("2230ee89-d961-4d24-a073-df3315ee7202");
        livroRepository.deleteById(id);
    }

    @Test
    @Transactional
    void buscarLivroTest() {
        UUID id = UUID.fromString("606cdb63-6dc3-4003-b9e6-f923cd176237");
        Livro livro = livroRepository.findById(id).orElse(null);
        System.out.println("Livro:");
        System.out.println(livro.getTitulo());
        System.out.println("Autor:");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaPorTituloTest() {
        List<Livro> lista = livroRepository.findByTitulo("Entendendo Algoritmos");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorISBNTest() {
        List<Livro> lista = livroRepository.findByIsbn("9837737-84874");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEPrecoTest() {
        var preco = BigDecimal.valueOf(100.00);
        var tituloPesquisa = "Entendendo Algoritmos";
        List<Livro> lista = livroRepository.findByTituloAndPreco(tituloPesquisa, preco);
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloOuIsbn() {
        var tituloPesquisa = "";
        var isbn = "9837737-84874";
        List<Livro> lista = livroRepository.findByTituloOrIsbn(tituloPesquisa, isbn);
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloEndingWithTest() {
        var tituloPesquisa = "algoritmos";
        List<Livro> lista = livroRepository.findByTituloEndingWithIgnoreCase(tituloPesquisa);
        lista.forEach(System.out::println);
    }

    // ########## JPQL @Query Tests ###################
    @Test
    void listarLivrosComQueryJPQL(){
        var resultado = livroRepository.listarLivrosOrdenadoPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    // Testando JOIN com JPQL
    @Test
    void listarAutoresDosLivros(){
        var resultado = livroRepository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }
    /* QUERY METHODS */
    @Test
    void listarDistinctTitulosLivros(){
        var resultado = livroRepository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeLivrosAutoresBrasileiros(){
        var resultado = livroRepository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }

    /* QUERY PARAMETERS , NAMED and POSITIONAL */
    @Test
    void listarPorGeneroQueryNamedParameters(){
        var resultado = livroRepository.findByGeneroNamedParameters(GeneroLivro.PROGRAMACAO, "preco");
        resultado.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroQueryPositionalParameters(){
        var resultado = livroRepository.findByGeneroPositionalParameters(GeneroLivro.PROGRAMACAO, "preco");
        resultado.forEach(System.out::println);
    }

    /* DELETE AND UPDATE com QUERY */
    @Test
    void deletarPorGeneroTest(){
        livroRepository.deleteByGenero(GeneroLivro.CIENCIA);
    }

    @Test
    void updateDataPublicacaoTest(){
        livroRepository.updateDataPublicacao(LocalDate.of(2000, 1, 1));
    }


}