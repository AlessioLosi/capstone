package eventi.capstone.Entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_creatore")
    private Utenti creatore;

    @OneToMany(mappedBy = "post")
    private List<Commenti> listaCommenti;

    private String testo;
    private LocalDateTime dataEOra;

    public Post(Utenti creatore, List<Commenti> listaCommenti, String testo, LocalDateTime dataEOra) {
        this.creatore = creatore;
        this.listaCommenti = listaCommenti;
        this.testo = testo;
        this.dataEOra = LocalDateTime.now();
    }
}
