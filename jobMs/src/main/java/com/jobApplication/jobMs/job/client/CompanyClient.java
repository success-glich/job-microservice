package com.jobApplication.jobMs.job.client;


import com.jobApplication.jobMs.job.dto.CompanyDTO;
import com.jobApplication.jobMs.job.utils.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "COMPANYMS")
public interface CompanyClient {

    @GetMapping("/companies/{id}")
    ApiResponse<CompanyDTO> getCompanyById(@PathVariable("id") Long id) ;

}
