package eventi.capstone.Payloads;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record NewEventDTO(
        @NotNull(message = "L'evento deve avere un nome")
        String nome,
        @NotNull(message = "Il campo artista non può essere vuoto")
        String artista,
        int postiDisponibili,
        LocalDate data,
        @NotNull(message = "Il campo luogo non può essere nullo")
        String luogo,
        double prezzo) {
}
