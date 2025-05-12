package com.jobApplication.jobMs.job.client;

import com.jobApplication.jobMs.job.exception.ResourceNotFoundException;
import com.jobApplication.jobMs.job.dto.CompanyDTO;
import com.jobApplication.jobMs.job.dto.CompanyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class CompanyServiceClient {


    @Autowired
    private  RestTemplate restTemplate;

    private final String companyServiceUrl;


    public CompanyServiceClient(@Value("${company.service.url}") String companyServiceUrl) {
        this.companyServiceUrl = companyServiceUrl;
    }

//    public CompanyServiceClient(RestTemplateBuilder restTemplateBuilder, @Value("${COMPANY_SERVICE_URL}")  String companyServiceUrl) {
//        this.restTemplate = restTemplateBuilder.build();
//        this.companyServiceUrl = companyServiceUrl;
//    }

    public CompanyDTO getCompanyById(Long companyId) {
        String url = companyServiceUrl + "/" + companyId;
        try {
            ResponseEntity<CompanyResponse> response = restTemplate.getForEntity(url, CompanyResponse.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null && response.getBody()!=null){
                    return  response.getBody().getData();

                }

            throw new RuntimeException("Failed to fetch company details. Status: " + response.getStatusCode());
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResourceNotFoundException("Company not found with id: " + companyId);
        }
    }

}