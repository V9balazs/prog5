package prog5.vizsga.beadando.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import prog5.vizsga.beadando.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
