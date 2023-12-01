package ch.zhaw.feelancer4u.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("jobStateChange")
public class JobStateChangeDTO {
    private String jobId;
    private String freelancerEmail;
}
