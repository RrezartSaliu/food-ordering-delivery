package org.example.order_service.Producer;

import lombok.RequiredArgsConstructor;
import org.example.PaymentEvent;
import org.example.order_service.Domain.DTO.CardInfoRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, PaymentEvent> paymentEventKafkaTemplate;

    private static boolean isNumeric(String value) {
        return value != null && value.matches("\\d+");
    }

    public void sendCheckoutEvent(CardInfoRequest cardInfoRequest, Integer total, String email) {

        if (
                cardInfoRequest.getCardNumber() == null ||
                        cardInfoRequest.getCvv() == null ||
                        !isNumeric(cardInfoRequest.getCardNumber()) ||
                        !isNumeric(cardInfoRequest.getCvv()) ||
                        cardInfoRequest.getCardNumber().length() != 16 ||
                        cardInfoRequest.getCvv().length() != 3
        ) {
            throw new IllegalArgumentException("Invalid card information");
        }

        PaymentEvent event = PaymentEvent.builder()
                .firstName(cardInfoRequest.getFirstName())
                .lastName(cardInfoRequest.getLastName())
                .cardNumber(cardInfoRequest.getCardNumber())
                .cvv(cardInfoRequest.getCvv())
                .amount(BigDecimal.valueOf(total))
                .email(email).build();

        System.out.println(email);
        System.out.println(event.getEmail());

        paymentEventKafkaTemplate.send("checkout-event", event);
    }
}
