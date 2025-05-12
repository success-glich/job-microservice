package com.jobApplication.jobMs.job.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class JobDTO {
    private Long id;
    private String title;
    private String description;
    private String location;
    private Double minSalary;
    private  Double maxSalary;
    CompanyDTO company;
    List<ReviewDTO> review;
}
