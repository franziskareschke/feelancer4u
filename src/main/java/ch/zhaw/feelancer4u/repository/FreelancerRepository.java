package ch.zhaw.feelancer4u.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import ch.zhaw.feelancer4u.model.Freelancer;

public interface FreelancerRepository extends MongoRepository<Freelancer, String> {
        Optional<Freelancer> findByEmail(String email);
        Freelancer findFirstByEmail(String email);
}
