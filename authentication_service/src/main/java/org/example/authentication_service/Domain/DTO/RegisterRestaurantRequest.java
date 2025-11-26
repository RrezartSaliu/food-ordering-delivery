package org.example.authentication_service.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRestaurantRequest {
    private String name;
    private String address;
    private String email;
    private String password;
}
