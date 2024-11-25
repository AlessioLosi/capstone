package eventi.capstone.Controllers;

import eventi.capstone.Entities.Eventi;
import eventi.capstone.Entities.User;
import eventi.capstone.Exceptions.BadRequestException;
import eventi.capstone.Payloads.NewEventDTO;
import eventi.capstone.Services.EventiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class EventiController {
    @Autowired
    private EventiService eventiService;

    @PostMapping("/me/eventi")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Eventi postEvento(@RequestBody @Validated NewEventDTO payload, @AuthenticationPrincipal User currentAuthenticatedUtente, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.eventiService.saveEvento(payload, currentAuthenticatedUtente);
    }

    @GetMapping("/me/eventi")
    @ResponseStatus(HttpStatus.OK)
    public Page<Eventi> findAllEventiPerUtente(
            String artista,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return this.eventiService.findAllByArtista(artista, page, size);
    }

    @DeleteMapping("/me/eventi/{id_evento}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteEvento(@PathVariable UUID id_evento, @AuthenticationPrincipal User currentAuthenticatedUtente) {
        this.eventiService.deleteEvento(id_evento, currentAuthenticatedUtente);
    }
}
