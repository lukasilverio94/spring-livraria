package io.github.cursodsousa.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor")
@Getter
@Setter
@ToString(exclude = "livros")
@EntityListeners(AuditingEntityListener.class) // vai ficar escutando toda vez que fizer alguma operacao nessa entidade
// e vai observar se tem aquelas anotations ("CreatedDate" ou "ModifiedDate")
// Pra funcionar o `@EntityListeners`, temos que ir na Main Application e
// adicinar @EnableJpaAuditing
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
    @OneToMany(// cascade = CascadeType.ALL)
            fetch = FetchType.LAZY, mappedBy = "autor")
    private List<Livro> livros;
    // @OneToMany por padrao tem fetch = "FetchType.LAZY"

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;

}
