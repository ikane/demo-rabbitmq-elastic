package com.example.demorabbitmqelastic.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "users", type = "customer")
@Data
@Builder(toBuilder = true)
public class Customer implements Serializable {
    @Id
    private String id;
    private String name;
    private String email;

    @Field(type = FieldType.Object, includeInParent = true)
    private Address address;

    public Customer() {
    }

    public Customer(String id, String name, String email, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
    }
}
