package org.example.authentication_service.Domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerProfile {
    @Id
    private Long id;
    @OneToOne
    @MapsId
    private AppUser appUser;
    private String firstName;
    private String lastName;
}
