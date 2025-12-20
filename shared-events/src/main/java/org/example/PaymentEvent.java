package org.example;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent {
    private String firstName;
    private String lastName;
    private String cardNumber;
    private String cvv;
    private BigDecimal amount;
    private boolean paid;
    private String email;
}
