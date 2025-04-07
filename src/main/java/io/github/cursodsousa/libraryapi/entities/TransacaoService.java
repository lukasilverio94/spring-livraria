package io.github.cursodsousa.libraryapi.entities;

import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import io.github.cursodsousa.libraryapi.model.Livro;
import io.github.cursodsousa.libraryapi.repository.AutorRepository;
import io.github.cursodsousa.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void salvarLivroComFoto(){
        // salva o livro
        // repository.save(livro);

        // pega o id do livro = livro.getId(); // Mesmo que nao tenha commitado, ja tem ID por causa do JPA
        // var id = livro.getId();

        // salvar foto do livro -> bucket na nuvem(nome arquivo vai ser id + formato)
        // bucketService.salvar(livro.getFoto(), id + ".png");

        // atualizar o nome do arquivo que foi salvo
        // livro.setNomeArquivoFoto(id + ".png");

        //!!!!!!! nao precisa agora de repository.save(livro);
        // pois como acima, se esta no managed e foi alterado sem exception, vai dar commit e atualizar o nosso DB.
    }
    @Transactional
    public void atualizacaoSemAtualizar(){
        UUID id = UUID.fromString("1c1477c8-ddc6-4b8a-ab52-a719eaf40842");
        var livro = livroRepository.findById(id).orElse(null);

        // atualizar livro
        livro.setDataPublicacao(LocalDate.of(2024, 6, 1));

    }

    @Transactional
    public void executar(){
        // Salva autor
        Autor autor = new Autor();
        autor.setNome("Lukas");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        // Salva Livro
        Livro livro = new Livro();
        livro.setIsbn("90887-84874");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Test livro Lucas");
        livro.setDataPublicacao(LocalDate.of(1980, 1, 2));

        livro.setAutor(autor);

        autorRepository.save(autor);

        livroRepository.save(livro);

        if(autor.getNome().equals("Teste Francisco")){
            throw new RuntimeException("Rollback!");
        }
    }
}
