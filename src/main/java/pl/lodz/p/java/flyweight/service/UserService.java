package pl.lodz.p.java.flyweight.service;

import java.util.Map;
import java.util.UUID;

public interface UserService {

    public void registerUser(Map<String, String> requestParams);


    public UUID getUserId(Map<String, String> requestParams);
}
