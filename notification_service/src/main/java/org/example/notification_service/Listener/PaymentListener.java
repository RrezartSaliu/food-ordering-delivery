package org.example.notification_service.Listener;

import lombok.RequiredArgsConstructor;
import org.example.PaymentEvent;
import org.example.notification_service.Service.EmailService;
import org.example.notification_service.util.WebSocketHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentListener {
    private final EmailService emailService;
    private final WebSocketHandler webSocketHandler;

    @RabbitListener(queues = "payment.queue")
    public void paymentEmail(PaymentEvent paymentEvent) {
        String status = paymentEvent.isPaid()?"made successfully":"failed";

        try{
            System.out.println(paymentEvent.getEmail());
            String subject = "Payment "+status;
            String text = "Hey "+ paymentEvent.getFirstName() + " " + paymentEvent.getLastName()+ " your payment with total: "
                    + paymentEvent.getAmount() + " MKD was "+ status;
            emailService.sendEmail(paymentEvent.getEmail(), subject, text);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        try {
            String wsMessage =
                    "{\"type\":\"payment\",\"status\":\"" + status + "\"}";

            webSocketHandler.sendToUser(paymentEvent.getUserId().toString(), wsMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
