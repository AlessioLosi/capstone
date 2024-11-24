package eventi.capstone.Payloads;

import java.time.LocalDate;

public record NewEventDTO(
        String nome,
        LocalDate data,
        int postiDisponibili) {
}
