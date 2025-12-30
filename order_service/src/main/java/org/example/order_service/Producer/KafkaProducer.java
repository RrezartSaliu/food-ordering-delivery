package org.example.order_service.Producer;

import lombok.RequiredArgsConstructor;
import org.example.PaymentEvent;
import org.example.order_service.Domain.DTO.CheckoutRequest;
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

    public void sendCheckoutEvent(CheckoutRequest checkoutRequest, Integer total, String email, Long userId) {

        if (
                checkoutRequest.getCardNumber() == null ||
                        checkoutRequest.getCvv() == null ||
                        !isNumeric(checkoutRequest.getCardNumber()) ||
                        !isNumeric(checkoutRequest.getCvv()) ||
                        checkoutRequest.getCardNumber().length() != 16 ||
                        checkoutRequest.getCvv().length() != 3 ||
                        checkoutRequest.getLatitude() == null ||
                        checkoutRequest.getLongitude() == null ||
                        checkoutRequest.getAddress() == null
        ) {
            throw new IllegalArgumentException("Invalid checkout information");
        }

        PaymentEvent event = PaymentEvent.builder()
                .firstName(checkoutRequest.getFirstName())
                .lastName(checkoutRequest.getLastName())
                .cardNumber(checkoutRequest.getCardNumber())
                .cvv(checkoutRequest.getCvv())
                .latitude(checkoutRequest.getLatitude())
                .longitude(checkoutRequest.getLongitude())
                .address(checkoutRequest.getAddress())
                .amount(BigDecimal.valueOf(total))
                .userId(userId)
                .email(email).build();


        paymentEventKafkaTemplate.send("checkout-event", event);
    }
}
