package org.example.authentication_service.Domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DriverProfile {
    @Id
    private Long id;
    @OneToOne
    @MapsId
    private AppUser appUser;
    private String firstName;
    private String lastName;
    private String vehicle;

    @ManyToOne
    @JoinColumn(name = "restaurant_profile_app_user_id")
    private RestaurantProfile restaurantProfile;
}
