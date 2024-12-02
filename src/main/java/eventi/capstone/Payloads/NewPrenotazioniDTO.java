package eventi.capstone.Payloads;

import java.util.UUID;

public record NewPrenotazioniDTO(
        UUID evento_id,
        String paymentMethod) {
}
