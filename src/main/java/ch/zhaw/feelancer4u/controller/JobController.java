package ch.zhaw.feelancer4u.controller;

import ch.zhaw.feelancer4u.model.Job;
import ch.zhaw.feelancer4u.model.JobCreateDTO;
import ch.zhaw.feelancer4u.model.JobStateAggregation;
import ch.zhaw.feelancer4u.model.JobType;
import ch.zhaw.feelancer4u.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

@RestController
@RequestMapping("/api")
public class JobController {

    @Autowired
    JobRepository jobRepository;

    @PostMapping("/job")
    @Secured("ROLE_admin")
    public ResponseEntity<Job> createJob(
            @RequestBody JobCreateDTO jDTO) {
        Job jDAO = new Job(jDTO.getDescription(), jDTO.getEarnings(),jDTO.getJobType());
        Job j = jobRepository.save(jDAO);
        return new ResponseEntity<>(j, HttpStatus.CREATED);
    }

    @GetMapping("/job")
    public ResponseEntity<Page<Job>> getAllJobs(@RequestParam(required = false) Double min, @RequestParam(required = false) JobType type,@RequestParam(required = false) Integer pageNumber,
@RequestParam(required = false) Integer pageSize) {
        Page<Job> jobs;
        if (min != null && type != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (min != null) {
            jobs = jobRepository.findByJobType(type, PageRequest.of(pageNumber - 1, pageSize));
        } 
        else if (type != null){
            jobs = jobRepository.findByEarningsGreaterThan(min, PageRequest.of(pageNumber - 1, pageSize));
        }
        else {
            jobs = jobRepository.findAll(PageRequest.of(pageNumber - 1, pageSize));
        }
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }

    @GetMapping("/job/aggregation/state")
    public List<JobStateAggregation> getJobStateAggregation() {
    return jobRepository.getJobStateAggregation();
    } 

    @DeleteMapping("/job")
@Secured("ROLE_admin")
public ResponseEntity<String> deleteAllJobs() {
jobRepository.deleteAll();
return ResponseEntity.status(HttpStatus.OK).body("DELETED");
}

}
