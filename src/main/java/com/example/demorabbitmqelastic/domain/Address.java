package com.example.demorabbitmqelastic.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Address  implements Serializable {
    private String street;
    private String zipCode;
    private String country;

}
