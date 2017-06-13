package com.example.java.domain.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.java.commons.enums.SecurityRoles;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "Users")
public class User {

    @Getter
    @Id
    private UUID id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String lastName;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Getter
    private String userRole;

    @Getter
    @Setter
    private String image;


    public User(String name, String lastName, String email, String password, String image) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.userRole = SecurityRoles.USER.getAsRoleName();
        this.image = image;
    }
}
