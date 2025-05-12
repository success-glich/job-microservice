package com.jobApplication.jobMs.job.mapper;

import com.jobApplication.jobMs.job.Job;
import com.jobApplication.jobMs.job.dto.CompanyDTO;
import com.jobApplication.jobMs.job.dto.JobDTO;
import com.jobApplication.jobMs.job.dto.ReviewDTO;

import java.util.List;

public class JobMapper {

    public static JobDTO mapToJobWithCompanyDto(Job job, CompanyDTO companyDTO , List <ReviewDTO> reviewDTO) {
        JobDTO jobDto = new JobDTO();
        jobDto.setId(job.getId());
        jobDto.setTitle(job.getTitle());
        jobDto.setDescription(job.getDescription());
        jobDto.setMaxSalary(job.getMaxSalary());
        jobDto.setMinSalary(job.getMinSalary());

        jobDto.setCompany(companyDTO);
        jobDto.setReview(reviewDTO);

        return jobDto;
    }
}
