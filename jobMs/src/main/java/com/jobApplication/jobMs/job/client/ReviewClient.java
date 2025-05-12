package com.jobApplication.jobMs.job.client;


import com.jobApplication.jobMs.job.dto.ReviewDTO;
import com.jobApplication.jobMs.job.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "REVIEWSMS")
public interface ReviewClient {

    @GetMapping("/reviews")
    ApiResponse<List<ReviewDTO>> getReviewByCompanyId(@RequestParam("companyId") Long id);
}
