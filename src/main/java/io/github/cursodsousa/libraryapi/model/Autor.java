package io.github.cursodsousa.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public") // public é o default pro schema
@Getter
@Setter
public class Autor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    // Relacao com Livros. Um autor pode ter muitos livros, entao:
    // Primeiro (One) se refere a Entidade atual (Autor) e Many a Livro.
    @OneToMany(mappedBy = "autor") // "mappedBy" vai dizer que nao tem essa coluna aqui, apenas mapeamento
    private List<Livro> livros;


}
