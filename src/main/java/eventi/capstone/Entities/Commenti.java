package eventi.capstone.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Post post;


    public Commenti(String testo) {

        this.testo = testo;
        this.data = LocalDateTime.now();
    }
}
