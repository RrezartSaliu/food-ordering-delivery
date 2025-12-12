package org.example.authentication_service.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.authentication_service.Domain.model.Role;

@Data
@AllArgsConstructor
public class RestaurantUserResponse {
    private Long id;
    private String email;
    private String name;
    private String address;
    private Role role;
}
