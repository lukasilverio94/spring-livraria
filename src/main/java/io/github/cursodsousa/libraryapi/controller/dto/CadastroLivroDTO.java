package io.github.cursodsousa.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import io.github.cursodsousa.libraryapi.model.GeneroLivro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record CadastroLivroDTO
        (@ISBN // validator for ISBN
        @NotBlank(message = "Campo obrigatório") String isbn,
        @NotBlank(message = "Campo obrigatório") String titulo,
        @NotNull(message = "Campo obrigatório") @Past(message = "Nao pode ser data futura") LocalDate dataPublicacao,
        GeneroLivro genero, BigDecimal preco,
        @NotNull(message = "Campo obrigatório") UUID idAutor) {

}
