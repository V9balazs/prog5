package prog5.vizsga.beadando.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import prog5.vizsga.beadando.entity.JobOpportunity;
import prog5.vizsga.beadando.repository.JobOpportunityRepository;

@Service
public class JobService {

    private final JobOpportunityRepository jobOpportunityRepository;

    @Autowired
    public JobService(JobOpportunityRepository jobOpportunityRepository) {
        this.jobOpportunityRepository = jobOpportunityRepository;
    }

    public List<JobOpportunity> getAllJobOpportunities() {
        return jobOpportunityRepository.findAll();
    }

    public JobOpportunity getJobOpportunityById(String id) {
        return jobOpportunityRepository.findById(id).orElse(null);
    }

    public JobOpportunity createOrUpdateJobOpportunity(JobOpportunity jobOpportunity) {
        return jobOpportunityRepository.save(jobOpportunity);
    }

    public void deleteJobOpportunity(String id) {
        jobOpportunityRepository.deleteById(id);
    }

    public void applyForJob(String jobId, String applicantUsername) {
        jobOpportunityRepository.findById(jobId).ifPresent(job -> {
            job.setApplicant(applicantUsername);
            jobOpportunityRepository.save(job);
        });
    }

    public void acceptJobOpportunity(String jobOpportunityId) {
        JobOpportunity jobOpportunity = jobOpportunityRepository.findById(jobOpportunityId).orElse(null);
        if (jobOpportunity != null) {
            jobOpportunity.setAccepted(true);
            jobOpportunityRepository.save(jobOpportunity);
        }
    }

    public void cancelApplication(String jobOpportunityId) {
        JobOpportunity jobOpportunity = jobOpportunityRepository.findById(jobOpportunityId).orElse(null);
        if (jobOpportunity != null) {
            jobOpportunity.setApplicant(null);
            jobOpportunityRepository.save(jobOpportunity);
        }
    }
}

