package eventi.capstone.Entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "prenotazioni")
public class Prenotazioni {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utenti utente;

    @ManyToOne
    @JoinColumn(name = "id_evento")
    private Eventi evento;

    private LocalDate data;

    public Prenotazioni(Utenti utente, Eventi evento, LocalDate data) {
        this.utente = utente;
        this.evento = evento;
        this.data = data;
    }
}
