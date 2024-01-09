package prog5.vizsga.beadando.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prog5.vizsga.beadando.entity.JobOpportunity;
import prog5.vizsga.beadando.service.JobService;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobOpportunity>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobOpportunities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobOpportunity> getJobById(@PathVariable String id) {
        JobOpportunity jobOpportunity = jobService.getJobOpportunityById(id);
        if (jobOpportunity == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(jobOpportunity);
        }
    }

    @PostMapping
    public ResponseEntity<JobOpportunity> createJob(@RequestBody JobOpportunity jobOpportunity) {
        JobOpportunity createdJob = jobService.createOrUpdateJobOpportunity(jobOpportunity);
        return ResponseEntity.ok(createdJob);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable String id) {
        jobService.deleteJobOpportunity(id);
        return ResponseEntity.ok().build();
    }
}


