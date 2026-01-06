package org.example.notification_service.Config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
        return messageConverter;
    }

    @Bean
    public Queue userRegisteredQueue() {
        return new Queue("user.registered.queue", true);
    }

    @Bean Queue paymentQueue() {
        return new Queue("payment.queue", true);
    }

    @Bean Queue orderCreatedQueue() {
        return new Queue("order.queue", true);
    }

    @Bean Queue orderDeliveredQueue() {
        return new Queue("order.delivered.queue", true);
    }
}
