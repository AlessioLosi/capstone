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
@Table(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_creatore")
    private User creatore;

    private String testo;
    private LocalDateTime data;

    public Post(User creatore, String testo, LocalDateTime dataEOra) {
        this.creatore = creatore;
        this.testo = testo;
        this.data = LocalDateTime.now();
    }
}
