package io.github.cursodsousa.libraryapi.controller.dto;

import java.util.List;

public record UsuarioDTO(String username, String password, List<String> roles) {
}
