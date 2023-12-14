package prog5.vizsga.beadando.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import prog5.vizsga.beadando.entity.JobOpportunity;

@Repository
public interface JobOpportunityRepository extends MongoRepository<JobOpportunity, String> {

}

