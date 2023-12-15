package prog5.vizsga.beadando.database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prog5.vizsga.beadando.entity.JobOpportunity;
import prog5.vizsga.beadando.repository.JobOpportunityRepository;

@Configuration
public class DataInitializer {

    /*@Bean
    CommandLineRunner initDatabase(JobOpportunityRepository repository) {
        return args -> {
            // Kezdő adatok létrehozása
            repository.save(new JobOpportunity("Budapest", "Java fejlesztő"));
            repository.save(new JobOpportunity("Pécs", "Manuális tesztelő"));
            // További adatok hozzáadása...
        };
    }*/
}