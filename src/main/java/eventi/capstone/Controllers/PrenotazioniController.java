package eventi.capstone.Controllers;

import com.stripe.exception.StripeException;
import eventi.capstone.Entities.Eventi;
import eventi.capstone.Entities.Prenotazioni;
import eventi.capstone.Entities.TipologiaPagamento;
import eventi.capstone.Entities.User;
import eventi.capstone.Payloads.NewPrenotazioniDTO;
import eventi.capstone.Services.EventiService;
import eventi.capstone.Services.PrenotazioniService;
import eventi.capstone.Services.UserService;
import eventi.capstone.Stripe.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping
public class PrenotazioniController {

    @Autowired
    private PrenotazioniService prenotazioniService;

    @Autowired
    private StripeService stripeService;

    @Autowired
    private EventiService eventiService;

    @Autowired
    private UserService userService;

    @PostMapping("me/prenotazione/{eventoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> prenota(@PathVariable UUID eventoId, @RequestBody NewPrenotazioniDTO payload, @AuthenticationPrincipal User currentAuthenticatedUtente) throws StripeException {

        Eventi evento = eventiService.findById(eventoId);

        if (prenotazioniService.existsByUserAndEventi(currentAuthenticatedUtente, evento)) {
            return ResponseEntity.badRequest().body("Hai già una prenotazione per questo evento.");
        }

        Prenotazioni prenotazione = new Prenotazioni();
        prenotazione.setEventi(evento);
        prenotazione.setUser(currentAuthenticatedUtente);
        prenotazione.setStatoPagamento(TipologiaPagamento.In_Attesa);
        Prenotazioni savedPrenotazione = prenotazioniService.savePrenotazione(prenotazione, currentAuthenticatedUtente);
        String paymentMethodId = payload.paymentMethod();
        String paymentIntentId = stripeService.createPaymentIntent((double) (evento.getPrezzo() * 100), "eur", paymentMethodId);

        savedPrenotazione.setPaymentIntentId(paymentIntentId);

        prenotazioniService.savePrenotazione(savedPrenotazione, currentAuthenticatedUtente);

        return ResponseEntity.ok(paymentIntentId);
    }
}
