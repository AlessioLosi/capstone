package eventi.capstone.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private Eventi eventi;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private TipologiaPagamento statoPagamento;
    private String paymentIntentId;

    public Prenotazioni(Eventi event, User user) {
        this.data = LocalDate.now();
        this.eventi = event;
        this.user = user;
    }
}
