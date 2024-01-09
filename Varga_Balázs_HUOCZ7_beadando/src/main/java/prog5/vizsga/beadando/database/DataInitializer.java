package prog5.vizsga.beadando.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import prog5.vizsga.beadando.entity.JobOpportunity;
import prog5.vizsga.beadando.entity.Role;
import prog5.vizsga.beadando.entity.User;
import prog5.vizsga.beadando.repository.JobOpportunityRepository;
import prog5.vizsga.beadando.repository.UserRepository;

@Configuration
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    CommandLineRunner initDatabase(JobOpportunityRepository jobOpportunityRepository, UserRepository userRepository) {
        return args -> {
            if (jobOpportunityRepository.count() == 0) {
                jobOpportunityRepository.save(new JobOpportunity("New York", "Java alkalmazás készítés"));
                jobOpportunityRepository.save(new JobOpportunity("London", "Manuális tesztelés"));
                jobOpportunityRepository.save(new JobOpportunity("Berlin", "Weboldal fejlesztés"));
                jobOpportunityRepository.save(new JobOpportunity("Bécs", "Web alkalmazás készítés"));
            }

            //userRepository.deleteAll();

            if (userRepository.count() == 0) {
                userRepository.save(new User("Manager", passwordEncoder.encode("manager"), Role.MANAGER));
                userRepository.save(new User("EmployeeA", passwordEncoder.encode("employeeA"), Role.EMPLOYEE));
                userRepository.save(new User("EmployeeB", passwordEncoder.encode("employeeB"), Role.EMPLOYEE));
                userRepository.save(new User("EmployeeC", passwordEncoder.encode("employeeC"), Role.EMPLOYEE));
            }
        };
    }
}