package eventi.capstone.Entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private LocalDateTime data;
    @ManyToOne
    @JoinColumn(name = "id_creatore")
    private User creatore;

    @ManyToOne
    @JoinColumn(name = "id_post")
    private Post post;


    public Commenti(User creatore, Post post, String testo, LocalDateTime data) {
        this.creatore = creatore;
        this.post = post;
        this.testo = testo;
        this.data = LocalDateTime.now();
    }
}
