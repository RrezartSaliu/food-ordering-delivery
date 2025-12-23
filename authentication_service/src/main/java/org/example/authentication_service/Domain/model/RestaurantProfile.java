package org.example.authentication_service.Domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestaurantProfile {
    @Id
    private Long id;
    @OneToOne
    @MapsId
    private AppUser appUser;
    private String name;
    private String address;
    private boolean verified;
}
