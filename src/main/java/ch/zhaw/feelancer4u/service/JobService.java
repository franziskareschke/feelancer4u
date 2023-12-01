package ch.zhaw.feelancer4u.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.feelancer4u.model.Freelancer;
import ch.zhaw.feelancer4u.model.Job;
import ch.zhaw.feelancer4u.model.JobState;
import ch.zhaw.feelancer4u.repository.FreelancerRepository;
import ch.zhaw.feelancer4u.repository.JobRepository;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private FreelancerRepository freelancerRepository; // Assuming this exists

    public Optional<Job> assignJob(String jobId, String freelancerEmail) {
        // 1. Check if the jobId is valid
        Optional<Job> jobToAssign = jobRepository.findById(jobId);
        if (!jobToAssign.isPresent()) {
            return Optional.empty();
        }

        Job job = jobToAssign.get();

        // 2. Check if the specified job is in the NEW state
        if (job.getJobState() != JobState.NEW) {
            return Optional.empty();
        }

        Freelancer freelancer = freelancerRepository.findFirstByEmail(freelancerEmail);
        if (freelancer == null) {
            return Optional.empty();
        }

        // 5. If all conditions are satisfied, perform the assignment.
        // 6. Set the job in the ASSIGNED state and store the freelancer ID with the
        // job.
        job.setJobState(JobState.ASSIGNED);
        job.setFreelancerId(freelancer.getId());
        jobRepository.save(job);
        return Optional.of(job);
    }

    public Optional<Job> completeJob(String jobId, String freelancerEmail) {
        // Get the job
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        if (!optionalJob.isPresent()) {
            return Optional.empty();
        }
        Job job = optionalJob.get();
    
        // Ensure the job is assigned to the freelancer and the job is in ASSIGNED state
        Freelancer freelancer = freelancerRepository.findFirstByEmail(freelancerEmail);
        if (freelancer == null 
                || !job.getFreelancerId().equals(freelancer.getId()) 
                || job.getJobState() != JobState.ASSIGNED) {
            return Optional.empty();
        }
    
        // Set job as done
        job.setJobState(JobState.DONE);
        jobRepository.save(job);
        
        return Optional.of(job);
    }

}

