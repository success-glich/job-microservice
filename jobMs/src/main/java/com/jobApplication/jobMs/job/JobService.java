package com.jobApplication.jobMs.job;


import com.jobApplication.jobMs.job.dto.JobRequest;
import com.jobApplication.jobMs.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> getAllJobs();
    Job createJob(JobRequest jobRequest);
    JobDTO getJobById(Long id);
    Job updateJobById(JobRequest jobRequest, Long id);
    void deleteJobById(Long id);
}
