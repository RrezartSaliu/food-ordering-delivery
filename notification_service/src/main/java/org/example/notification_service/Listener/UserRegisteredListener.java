package org.example.notification_service.Listener;

import lombok.RequiredArgsConstructor;
import org.example.UserRegisteredEvent;
import org.example.notification_service.Service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserRegisteredListener {
    private static final Logger log = LoggerFactory.getLogger(UserRegisteredListener.class);
    private final EmailService emailService;

    @RabbitListener(queues = "user.registered.queue")
    public void handleUserRegistered(UserRegisteredEvent userRegisteredEvent) {
        try {
            log.info("Received UserRegisteredEvent: {}", userRegisteredEvent);
            String subject = "Welcome email!";
            String text = String.format("Hello %s, welcome to our platform", userRegisteredEvent.getName());
            emailService.sendEmail(userRegisteredEvent.getEmail(),  subject, text);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
