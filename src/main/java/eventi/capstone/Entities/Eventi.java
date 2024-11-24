package eventi.capstone.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "eventi")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Eventi {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String nome;
    private int postiDisponibili;
    private LocalDate data;
    @ManyToOne
    @JoinColumn(name = "id_organizzatore")
    private User organizzatore;

    public Eventi(String nome, int postiDisponibili, LocalDate data, User organizzatore) {
        this.nome = nome;
        this.postiDisponibili = postiDisponibili;
        this.data = data;
        this.organizzatore = organizzatore;
    }
}
