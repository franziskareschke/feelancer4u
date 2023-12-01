package ch.zhaw.feelancer4u.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import ch.zhaw.feelancer4u.model.Job;
import ch.zhaw.feelancer4u.model.JobStateAggregation;
import ch.zhaw.feelancer4u.model.JobType;
import org.springframework.data.domain.Page;


public interface JobRepository extends MongoRepository<Job, String> {
    Page<Job> findByEarningsGreaterThan(Double earnings, Pageable pageable);
    Page<Job> findByJobType(JobType jobType, Pageable pageable);
    @Aggregation("{$group:{_id:'$jobState',jobIds:{$push:'$_id',},count:{$count:{}}}}")
    List<JobStateAggregation> getJobStateAggregation();
}