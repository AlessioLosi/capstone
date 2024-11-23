package eventi.capstone.Entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "eventi")
public class Eventi {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID idEvento;

    private String artista;
    private String luogo;
    private Integer postiDisponibili;

    @ManyToOne
    @JoinColumn(name = "creatore_evento")
    private Utenti creatoreEvento;

    public Eventi(String artista, String luogo, Integer postiDisponibili, Utenti creatoreEvento) {
        this.artista = artista;
        this.luogo = luogo;
        this.postiDisponibili = postiDisponibili;
        this.creatoreEvento = creatoreEvento;
    }

}
