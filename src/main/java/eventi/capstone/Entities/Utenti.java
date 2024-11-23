package eventi.capstone.Entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "utenti")
public class Utenti {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    private String cognome;
    private String email;
    private String username;
    @Enumerated(EnumType.STRING)
    private TipologiaUtente tipologiaUtente;
    @OneToMany(mappedBy = "creatore")
    private List<Post> listaPost;

    @OneToMany(mappedBy = "utente")
    private List<Prenotazioni> listaPrenotazioni;

    public Utenti(String nome, String cognome, String email, String username, TipologiaUtente tipologiaUtente) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.username = username;
        this.tipologiaUtente = TipologiaUtente.USER;
    }
}
