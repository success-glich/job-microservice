package com.jobApplication.jobMs.job.Impl;


import com.jobApplication.jobMs.job.client.CompanyClient;
import com.jobApplication.jobMs.job.client.ReviewClient;
import com.jobApplication.jobMs.job.dto.ReviewDTO;
import com.jobApplication.jobMs.job.exception.ResourceNotFoundException;
import com.jobApplication.jobMs.job.Job;
import com.jobApplication.jobMs.job.JobRepo;
import com.jobApplication.jobMs.job.JobService;
import com.jobApplication.jobMs.job.dto.CompanyDTO;
import com.jobApplication.jobMs.job.dto.JobRequest;
import com.jobApplication.jobMs.job.dto.JobDTO;
import com.jobApplication.jobMs.job.mapper.JobMapper;
import com.jobApplication.jobMs.job.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {


    @Autowired
    private JobRepo jobRepo;

    @Autowired
    private   CompanyClient companyClient;

    @Autowired
    private ReviewClient reviewClient;


    private CompanyDTO fetchCompanyDetails(Long companyId) {
        ApiResponse<CompanyDTO> response = companyClient.getCompanyById(companyId);

        if (!response.isSuccess() || response.getData() == null) {
            throw  new ResourceNotFoundException("Company not found with id: " + companyId);
        }
        return response.getData();
    }
    private  List<ReviewDTO> fetchReviewDetails(Long companyId) {
        ApiResponse<List<ReviewDTO>> response = reviewClient.getReviewByCompanyId(companyId);
//        if (!response.isSuccess() || response.getData() == null) {
//            throw  new ResourceNotFoundException("Review not found with id: " + companyId);
//        }
        return response.getData() == null ? List.of() : response.getData();
    }

    private JobDTO convertToDto(Job job){
        CompanyDTO companyDTO = this.fetchCompanyDetails(job.getCompanyId());

         List<ReviewDTO> reviews = this.fetchReviewDetails(job.getCompanyId());

        return JobMapper.mapToJobWithCompanyDto(job, companyDTO , reviews);
    }

    @Override
    public List<JobDTO> getAllJobs() {

        return  jobRepo.findAll().stream().map(this::convertToDto).toList();
    }

    @Override
    public Job createJob(JobRequest jobRequest) {

        Job job = new Job();
        job.setTitle(jobRequest.getTitle());
        job.setDescription(jobRequest.getDescription());
        job.setMinSalary(jobRequest.getMinSalary());
        job.setMaxSalary(jobRequest.getMaxSalary());
        CompanyDTO company = this.fetchCompanyDetails(jobRequest.getCompanyId());
        job.setCompanyId(company.getId());

        return jobRepo.save(job);
    }


    @Override
    public JobDTO getJobById(Long id) {

        Job job = jobRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Job not found with id: "+id));
        return convertToDto(job);
    }



    @Override
    public Job updateJobById(JobRequest jobRequest, Long id) {
        Job existingJob = jobRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Job not found with id: "+id));
        existingJob.setTitle(jobRequest.getTitle());
        existingJob.setDescription(jobRequest.getDescription());
        existingJob.setMinSalary(jobRequest.getMinSalary());
        existingJob.setMaxSalary(jobRequest.getMaxSalary());

        return jobRepo.save(existingJob);
    }

    @Override
    public void deleteJobById(Long id) {
        Job existingJob = jobRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Job not found with id: "+id));
        jobRepo.delete(existingJob);
    }

}
