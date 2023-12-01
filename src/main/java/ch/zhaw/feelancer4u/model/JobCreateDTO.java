package ch.zhaw.feelancer4u.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JobCreateDTO {
    private String description;
    private Double earnings;
    private JobType jobType;
}