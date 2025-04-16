package io.github.cursodsousa.libraryapi.controller.mappers;

import io.github.cursodsousa.libraryapi.controller.dto.CadastroLivroDTO;
import io.github.cursodsousa.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import io.github.cursodsousa.libraryapi.model.Autor;
import io.github.cursodsousa.libraryapi.model.Livro;
import io.github.cursodsousa.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;

    @Mapping(
            target = "autor",
            expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )"
    )
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);

    public void updateEntityFromDto(CadastroLivroDTO dto, Livro livro){
        livro.setIsbn(dto.isbn());
        livro.setTitulo(dto.titulo());
        livro.setDataPublicacao(dto.dataPublicacao());
        livro.setGenero(dto.genero());
        livro.setPreco(dto.preco());

        Autor autor = autorRepository.findById(dto.idAutor())
                .orElseThrow( () -> new IllegalArgumentException("Autor não encontrado"));
        livro.setAutor(autor);
    }
}