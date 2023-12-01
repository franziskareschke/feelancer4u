package ch.zhaw.feelancer4u.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ch.zhaw.feelancer4u.model.Job;
import ch.zhaw.feelancer4u.model.JobStateChangeDTO;
import ch.zhaw.feelancer4u.service.JobService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    @Autowired
    JobService jobService;

    @PutMapping("/assignjob")
    @Secured("ROLE_admin")
    public ResponseEntity<Job> assignJob(@RequestBody JobStateChangeDTO changeS) {
        String freelancerEmail = changeS.getFreelancerEmail();
        String jobId = changeS.getJobId();
        Optional<Job> job = jobService.assignJob(jobId, freelancerEmail);
        if (job.isPresent()) {
            return new ResponseEntity<>(job.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/me/assignjob")
    public ResponseEntity<Job> assignToMe(@RequestParam String jobId,
            @AuthenticationPrincipal Jwt jwt) {
        String userEmail = jwt.getClaimAsString("email");
        Optional<Job> job = jobService.assignJob(jobId, userEmail);
        if (job.isPresent()) {
            return new ResponseEntity<>(job.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/completejob")
    @Secured("ROLE_admin")
    public ResponseEntity<Job> completeJob(@RequestBody JobStateChangeDTO jobStateChange) {
        String freelancerEmail = jobStateChange.getFreelancerEmail();
        String jobId = jobStateChange.getJobId();

        Optional<Job> completedJob = jobService.completeJob(jobId, freelancerEmail);
        if (completedJob.isPresent()) {
            return new ResponseEntity<>(completedJob.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/me/completejob")
    @Secured("ROLE_admin")
    public ResponseEntity<Job> completeJob(@RequestParam String jobId,
            @AuthenticationPrincipal Jwt jwt) {
                System.out.println("i am");
        String userEmail = jwt.getClaimAsString("email");
        Optional<Job> job = jobService.completeJob(jobId, userEmail);

        if (job.isPresent()) {
            return new ResponseEntity<>(job.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
