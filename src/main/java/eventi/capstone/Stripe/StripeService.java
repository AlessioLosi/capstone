package eventi.capstone.Stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    public String createCardToken(String cardNumber, String expMonth, String expYear, String cvc) throws StripeException {
        Map<String, Object> card = new HashMap<>();
        card.put("number", cardNumber);
        card.put("exp_month", expMonth);
        card.put("exp_year", expYear);
        card.put("cvc", cvc);

        Map<String, Object> params = new HashMap<>();
        params.put("card", card);

        Token token = Token.create(params);
        return token.getId();
    }

    public String createPaymentIntent(double totale, String currency, String paymentMethod) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("amount", (totale) * 100);
        params.put("currency", "EUR");
        params.put("payment_method", paymentMethod);
        params.put("confirm", true);
        params.put("capture_method", "automatic");

        PaymentIntent paymentIntent = PaymentIntent.create(params);
        return paymentIntent.getId();
    }
}
