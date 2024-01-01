package prog5.vizsga.beadando.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prog5.vizsga.beadando.entity.JobOpportunity;
import prog5.vizsga.beadando.repository.JobOpportunityRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(JobOpportunityRepository repository) {
        return args -> {
            repository.save(new JobOpportunity("New York", "Java alkalmazás készítés"));
            repository.save(new JobOpportunity("London", "Manuális tesztelés"));
            repository.save(new JobOpportunity("Berlin", "Weboldal fejlesztés"));
            repository.save(new JobOpportunity("Bécs", "Web alkalmazás készítés"));
        };
    }
}