package org.example.order_service.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardInfoRequest {
    private String firstName;
    private String lastName;
    private String cardNumber;
    private String cvv;
}
