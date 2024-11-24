package eventi.capstone.Payloads;

import jakarta.validation.constraints.NotNull;

public record PostDTO(
        @NotNull(message = "il testo non puo essere nullo")
        String testo
) {
}
