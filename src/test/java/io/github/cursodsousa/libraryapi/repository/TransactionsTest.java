package io.github.cursodsousa.libraryapi.repository;

import io.github.cursodsousa.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionsTest {

    @Autowired
    TransacaoService transacaoService;

    @Test
    void transacaoSimples(){
        transacaoService.executar();
    }
    @Test
    void transacaoEstadoManaged(){
        transacaoService.atualizacaoSemAtualizar();
    }
}
