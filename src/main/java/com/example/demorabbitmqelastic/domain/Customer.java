package com.example.demorabbitmqelastic.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Customer implements Serializable {
    private String name;
    private String email;
    private Address address;
}
