package ch.zhaw.feelancer4u.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Document("job")
public class Job {

    @Id
    private String id;
    @NonNull
    private String description;
    @NonNull
    private Double earnings;
    @NonNull
    private JobType jobType;
    private String freelancerId;
    private JobState jobState = JobState.NEW;
}