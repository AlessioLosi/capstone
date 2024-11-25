package eventi.capstone.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Prenotazioni {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private LocalDate data;
    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Eventi eventi;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Prenotazioni(Eventi event, User user) {
        this.data = LocalDate.now();
        this.eventi = event;
        this.user = user;
    }
}
