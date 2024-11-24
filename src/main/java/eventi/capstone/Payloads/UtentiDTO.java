package eventi.capstone.Payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record UtentiDTO(
        @NotEmpty(message = "il nome è obbligatorio")
        @Size(min = 2, max = 20, message = "Il nome deve essere compreso tra 2 e 20 caratteri!")
        String nome,
        @NotEmpty(message = "il cognome è obbligatorio")
        @Size(min = 2, max = 40, message = "Il cognome deve essere compreso tra 2 e 40 caratteri!")
        String cognome,
        @Email(message = "L'email inserita non è un'email valida")
        String email,
        @NotEmpty(message = "l'username è obbligatorio")
        @Size(min = 2, max = 40, message = "Lo user deve essere compreso tra 2 e 20 caratteri!")
        String username,
        @NotEmpty(message = "il nome è obbligatorio")
        String password

) {
}
