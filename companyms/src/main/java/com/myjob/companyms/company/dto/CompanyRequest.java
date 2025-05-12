package com.myjob.companyms.company.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyRequest {

    @NotBlank(message = "Company name is required.")
    private String name;

    @NotBlank(message ="Description is required.")
    private String description;



}
