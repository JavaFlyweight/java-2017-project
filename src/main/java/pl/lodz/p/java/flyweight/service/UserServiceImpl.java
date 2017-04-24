package pl.lodz.p.java.flyweight.service;

import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pl.lodz.p.java.flyweight.model.User;
import pl.lodz.p.java.flyweight.repository.UserRepository;

@Service
@Validated
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Inject
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private boolean checkUserExist(String email) {
        User user = userRepository.findOneByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
}
