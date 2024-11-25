package eventi.capstone.Controllers;

import eventi.capstone.Entities.User;
import eventi.capstone.Payloads.UtentiDTO;
import eventi.capstone.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PrenotazioniService prenotazioniService;

    @Autowired
    private EventiService eventiService;
    @Autowired
    private PostService postService;
    @Autowired
    private CommentiService commentiService;

    //gestione utente
    @GetMapping("/me")
    public User getProfile(@AuthenticationPrincipal User currentAuthenticatedUtente) {
        return currentAuthenticatedUtente;
    }

    @PutMapping("/me")
    public User updateProfile(@AuthenticationPrincipal User currentAuthenticatedUtente, @RequestBody @Validated UtentiDTO body) {
        return this.userService.findByIdAndUpdate(currentAuthenticatedUtente.getId(), body);
    }
    // gestione eventi


}
