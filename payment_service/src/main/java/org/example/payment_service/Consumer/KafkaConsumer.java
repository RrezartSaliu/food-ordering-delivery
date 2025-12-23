package org.example.payment_service.Consumer;

import lombok.RequiredArgsConstructor;
import org.example.PaymentEvent;
import org.example.payment_service.Config.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class KafkaConsumer {
    private final RabbitTemplate rabbitTemplate;
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    @KafkaListener(topics = "checkout-event", groupId = "payment-service-group")
    public void consume(PaymentEvent paymentEvent) {
        System.out.println("Received Payment Event: " + paymentEvent);

        boolean successPayment = new Random().nextInt(10) < 8;

        if(successPayment) {
            paymentEvent.setPaid(true);
            rabbitTemplate.convertAndSend(RabbitConfig.PAYMENT_QUEUE, paymentEvent);
            kafkaTemplate.send("successful-payment", paymentEvent);
            //send notification service order made
            //send order service order to create
        }
        else {
            paymentEvent.setPaid(false);
            rabbitTemplate.convertAndSend(RabbitConfig.PAYMENT_QUEUE, paymentEvent);
            //send notification service payment failed
        }
    }
}
