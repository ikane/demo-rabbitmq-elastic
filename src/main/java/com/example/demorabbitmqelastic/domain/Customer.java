package com.example.demorabbitmqelastic.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "users", type = "customer")
@Data
@Builder
public class Customer implements Serializable {
    @Id
    private String id;
    private String name;
    private String email;

    @Field(type = FieldType.Nested, includeInParent = true)
    private Address address;
}
