package eventi.capstone.Payloads;

import jakarta.validation.constraints.NotEmpty;

public record CommentiDTO(
        @NotEmpty(message = "il testo non può essere nullo")
        String testo
) {
}
