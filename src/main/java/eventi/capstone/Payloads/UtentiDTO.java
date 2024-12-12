package eventi.capstone.Payloads;

import jakarta.validation.constraints.Email;

public record UtentiDTO(
        String nome,
        String cognome,
        @Email(message = "L'email inserita non è un'email valida")
        String email,
        String username,
        String password

) {
}
