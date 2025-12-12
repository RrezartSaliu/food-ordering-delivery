package org.example.authentication_service.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.authentication_service.Domain.model.Role;

@Data
@AllArgsConstructor
public class DriverUserResponse {
    private String firstName;
    private String lastName;
    private String vehicle;
    private String email;
    private Role role;
}
