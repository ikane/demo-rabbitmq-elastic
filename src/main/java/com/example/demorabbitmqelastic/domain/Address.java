package com.example.demorabbitmqelastic.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder(toBuilder = true)
public class Address  implements Serializable {
    private String street;
    private String zipCode;
    private String country;

    public Address() {
    }

    public Address(String street, String zipCode, String country) {
        this.street = street;
        this.zipCode = zipCode;
        this.country = country;
    }
}
