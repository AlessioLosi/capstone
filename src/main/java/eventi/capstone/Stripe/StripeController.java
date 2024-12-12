package eventi.capstone.Stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import eventi.capstone.Entities.Eventi;
import eventi.capstone.Entities.Prenotazioni;
import eventi.capstone.Entities.TipologiaPagamento;
import eventi.capstone.Entities.User;
import eventi.capstone.Services.EventiService;
import eventi.capstone.Services.PrenotazioniService;
import eventi.capstone.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class StripeController {

    @Autowired
    private EventiService eventiService;

    @Autowired
    private PrenotazioniService prenotazioniService;

    @Autowired
    private UserService userService;


    @PostMapping("/checkout/{eventId}")
    public String createCheckoutSession(@AuthenticationPrincipal User currentAuthenticatedUtente, @PathVariable UUID eventId) throws StripeException {

        Eventi evento = eventiService.findById(eventId);


        User user = currentAuthenticatedUtente;
        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(evento.getNome())
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("eur")  // Valuta
                        .setUnitAmount((long) (evento.getPrezzo() * 100))
                        .setProductData(productData)
                        .build();


        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setPriceData(priceData)
                        .setQuantity(1L)
                        .build();


        SessionCreateParams params =
                SessionCreateParams.builder()
                        .addLineItem(lineItem)
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:3000/success")
                        .setCancelUrl("http://localhost:3000/dashboardUser")
                        .build();

        Session session = Session.create(params);

        Prenotazioni prenotazione = new Prenotazioni(evento, user);
        prenotazione.setPaymentIntentId(session.getId());
        prenotazioniService.savePrenotazione(prenotazione, currentAuthenticatedUtente);
        eventiService.updatePosti(eventId);

        return session.getUrl();
    }


    @PostMapping("/webhook")
    public void handleStripeWebhook(@RequestBody String payload, @AuthenticationPrincipal User currentAuthenticatedUtente) {
        try {
            String eventPayload = payload;


            if (eventPayload != null) {
                String sessionId = "extracted-session-id";

                Prenotazioni prenotazione = prenotazioniService.findByPaymentIntentId(sessionId);
                prenotazione.setStatoPagamento(TipologiaPagamento.Completato);
                prenotazioniService.savePrenotazione(prenotazione, currentAuthenticatedUtente);
            }
        } catch (Exception e) {
            System.err.println("Errore nel gestire il webhook: " + e.getMessage());
        }
    }
}

