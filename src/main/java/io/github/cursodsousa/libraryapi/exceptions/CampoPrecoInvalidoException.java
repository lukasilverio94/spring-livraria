package io.github.cursodsousa.libraryapi.exceptions;

import lombok.Getter;

public class CampoPrecoInvalidoException extends RuntimeException{

    @Getter
    private String campo;

    public CampoPrecoInvalidoException(String campo, String mensagem){
        super(mensagem);
        this.campo = campo;
    }
}
