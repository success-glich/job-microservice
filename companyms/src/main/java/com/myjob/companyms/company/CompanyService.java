package com.myjob.companyms.company;

import com.myjob.companyms.company.dto.CompanyRequest;
import com.myjob.companyms.company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Company createCompany(CompanyRequest companyRequest);
    Company getCompanyById(Long id);
    Company updateCompany(Long id, CompanyRequest companyRequest);
    void deleteCompanyById(Long id);
     void updateCompanyRating(ReviewMessage reviewMessage);
}
