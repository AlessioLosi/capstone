package eventi.capstone.Controllers;

import eventi.capstone.Entities.Commenti;
import eventi.capstone.Entities.User;
import eventi.capstone.Exceptions.BadRequestException;
import eventi.capstone.Payloads.CommentiDTO;
import eventi.capstone.Services.CommentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class CommentiController {
    @Autowired
    private CommentiService commentiService;

    @PostMapping("/me/commento")
    @ResponseStatus(HttpStatus.CREATED)
    public Commenti commenti(@RequestBody @Validated CommentiDTO payload, @AuthenticationPrincipal User currentAuthenticatedUtente, BindingResult validationResult, Long id) {
        if (validationResult.hasErrors()) {
            validationResult.getAllErrors().forEach(System.out::println);
            throw new BadRequestException("Ci sono stati errori nel payload!");
        }
        return this.commentiService.saveCommenti(payload, currentAuthenticatedUtente, id);
    }

    @GetMapping("/me/commento/post")
    @ResponseStatus(HttpStatus.OK)
    public Page<Commenti> findCommentiByPost(
            Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return this.commentiService.findAllByPost(id, page, size);
    }

    @GetMapping("/me/commenti/utente")
    @ResponseStatus(HttpStatus.OK)
    public Page<Commenti> findCommentiByCreatore(
            @AuthenticationPrincipal User currentAuthenticatedUtente,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return this.commentiService.findAllByCreatore(currentAuthenticatedUtente, page, size);
    }

    @DeleteMapping("/me/commento/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable UUID id, @AuthenticationPrincipal User currentAuthenticatedUtente) {
        this.deletePost(id, currentAuthenticatedUtente);
    }

    @PutMapping("/me/commento")
    public Commenti updateCommenti(Long id, @AuthenticationPrincipal User currentAuthenticatedUtente, @RequestBody @Validated CommentiDTO body) {
        return this.commentiService.findByIdAndUpdate(id, body, currentAuthenticatedUtente);
    }
}
