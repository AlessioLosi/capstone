package eventi.capstone.Controllers;

import eventi.capstone.Entities.Prenotazioni;
import eventi.capstone.Entities.User;
import eventi.capstone.Services.PrenotazioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class PrenotazioniController {
    @Autowired
    private PrenotazioniService prenotazioniService;

    @GetMapping("/me/prenotazioni")
    @ResponseStatus(HttpStatus.OK)
    public Page<Prenotazioni> findPostByCreatore(
            @AuthenticationPrincipal User currentAuthenticatedUtente,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return this.prenotazioniService.findAllByUser(currentAuthenticatedUtente, page, size);
    }


    @DeleteMapping("/me/prenotazioni/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEvento(@PathVariable UUID id, @AuthenticationPrincipal User currentAuthenticatedUtente) {
        this.prenotazioniService.deletePrenotazione(id, currentAuthenticatedUtente);
    }

}
