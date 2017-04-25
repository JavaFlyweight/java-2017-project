package pl.lodz.p.java.flyweight.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

public class User {

    @Id
    @Getter
    private UUID id;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;


    public User(String email, String password) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = password;
    }
}
