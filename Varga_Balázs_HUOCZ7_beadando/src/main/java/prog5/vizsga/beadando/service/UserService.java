package prog5.vizsga.beadando.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import prog5.vizsga.beadando.entity.User;
import prog5.vizsga.beadando.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUserIdFromUsername(String username) {
        User user = userRepository.findByUsername(username);
        return user != null ? user.getId() : null;
    }
}

