package com.jobApplication.jobMs.job.dto;

import lombok.Data;


@Data
public class JobRequest {
    private String title;
    private String description;
    private String location;
    private Double minSalary;
    private  Double maxSalary;

    private Long companyId;

}
