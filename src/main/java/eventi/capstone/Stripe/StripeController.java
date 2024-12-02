package eventi.capstone.Stripe;


import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/webhook")
public class StripeController {

    private static final String ENDPOINT_SECRET = "whsec_xxxxxxxxx";

    @PostMapping
    public String handleStripeEvent(HttpServletRequest request) {
        String payload;
        try {
            payload = new String(request.getInputStream().readAllBytes());
        } catch (IOException e) {
            return "Errore nel leggere il payload";
        }

        String sigHeader = request.getHeader("Stripe-Signature");

        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, ENDPOINT_SECRET);
        } catch (Exception e) {
            return "Errore nella verifica del webhook";
        }

        if ("payment_intent.succeeded".equals(event.getType())) {
            PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
            System.out.println("Pagamento riuscito: " + paymentIntent.getId());
        }

        return "Webhook ricevuto";
    }
}

