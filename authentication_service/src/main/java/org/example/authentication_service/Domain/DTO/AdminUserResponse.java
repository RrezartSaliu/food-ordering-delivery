package org.example.authentication_service.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.authentication_service.Domain.model.Role;

@Data
@AllArgsConstructor
public class AdminUserResponse {
    private Long id;
    private String username;
    private String email;
    private Role role;
}
