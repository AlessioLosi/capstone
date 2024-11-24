package eventi.capstone.Payloads;

import java.time.LocalDate;
import java.util.UUID;

public record NewPrenotazioniDTO(
        Long Id,
        LocalDate data,

        UUID utente_id,
        UUID evento_id) {
}
