package com.example.demorabbitmqelastic.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@Document(indexName = "users", type = "customer")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {
    @Id
    private String id;
    private String name;
    private String email;
    //@Field(type = Text, fielddata = true)
    private String gender;
    private Boolean married;

    @Field(type = FieldType.Object, includeInParent = true)
    private Address address;

}
