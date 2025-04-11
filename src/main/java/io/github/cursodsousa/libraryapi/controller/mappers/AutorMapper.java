package io.github.cursodsousa.libraryapi.controller.mappers;

import io.github.cursodsousa.libraryapi.controller.dto.AutorDTO;
import io.github.cursodsousa.libraryapi.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // Transformar o mapper em Component spring
public interface AutorMapper {

    // Transforma DTO para Entity
    Autor toEntity(AutorDTO dto);

    // Transforma Entity para DTO
    AutorDTO toDto(Autor autor);
}
