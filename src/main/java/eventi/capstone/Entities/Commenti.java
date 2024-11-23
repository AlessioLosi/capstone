package eventi.capstone.Entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "commenti")
public class Commenti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String testo;
    @ManyToOne
    @JoinColumn(name = "id_creatore")
    private Utenti creatore;

    @ManyToOne
    @JoinColumn(name = "id_post")
    private Post post;


    public Commenti(Utenti creatore, Post post, String testo) {
        this.creatore = creatore;
        this.post = post;
        this.testo = testo;
    }
}
