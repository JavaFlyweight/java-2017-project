package pl.lodz.p.java.flyweight.model;

import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    
        @Id
    private long id;
        private String email ;
        private String password;
    
}
