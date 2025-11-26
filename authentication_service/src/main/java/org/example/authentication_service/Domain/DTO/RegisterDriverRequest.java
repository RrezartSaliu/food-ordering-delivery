package org.example.authentication_service.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDriverRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String vehicle;
}
