package pl.lodz.p.java.flyweight.repository;

import java.util.UUID;

import pl.lodz.p.java.flyweight.model.User;

public interface UserRepositoryCustom {

    public User findOneByEmailAndPassword(String email, String password);


    public User findOneByUserId(UUID id);
}
