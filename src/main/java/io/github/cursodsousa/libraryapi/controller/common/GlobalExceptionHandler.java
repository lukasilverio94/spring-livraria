package io.github.cursodsousa.libraryapi.controller.common;

import io.github.cursodsousa.libraryapi.dto.ErroCampo;
import io.github.cursodsousa.libraryapi.dto.ErroResposta;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice // annotation que vai capturar exceptions e dar resposta Rest
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) // passar qual Exception vai capturar
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // mapear retorno desse método (400, 404, o que quisermos) pois no body do metodo abaixo, nao é retornado o status.
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
    //    Lembrando que se espera isso do ErroResposta:
    //    int status, String mensagem, List<ErroCampo> erros
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaDeErros = fieldErrors
                .stream()
                .map(fe -> new ErroCampo(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErroResposta(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validacao.", listaDeErros);
    }

    /*
    Toda a vez que der o 'MethodArgumentNotValidException' no nosso código, vai entrar neste handler
     */
}
