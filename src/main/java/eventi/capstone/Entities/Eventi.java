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
    private String artista;
    private String luogo;
    private int postiDisponibili;
    private LocalDate data;
    private double prezzo;
    @ManyToOne
    @JoinColumn(name = "id_organizzatore")
    private User organizzatore;

    public Eventi(String nome, String artista, int postiDisponibili, LocalDate data, String luogo, double prezzo) {
        this.nome = nome;
        this.artista = artista;
        this.postiDisponibili = postiDisponibili;
        this.data = data;
        this.luogo = luogo;
        this.prezzo = prezzo;
    }
}
