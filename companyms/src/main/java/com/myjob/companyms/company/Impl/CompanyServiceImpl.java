package com.myjob.companyms.company.Impl;

import com.myjob.companyms.company.Company;
import com.myjob.companyms.company.CompanyRepo;
import com.myjob.companyms.company.CompanyService;
import com.myjob.companyms.company.clients.ReviewClient;
import com.myjob.companyms.company.dto.CompanyRequest;
import com.myjob.companyms.company.dto.ReviewMessage;
import com.myjob.companyms.company.exception.ResourceNotFoundException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    ReviewClient reviewClient;


    @Override
    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    @Override
    public Company createCompany(CompanyRequest companyRequest) {
       Company newCompany = new Company();
       newCompany.setName(companyRequest.getName());
       newCompany.setDescription(companyRequest.getDescription());

       return companyRepo.save(newCompany);
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));
    }

    @Override
    public Company updateCompany(Long id, CompanyRequest companyRequest) {
        Company existingCompany = companyRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));
        existingCompany.setName(companyRequest.getName());
        existingCompany.setDescription(companyRequest.getDescription());
        return companyRepo.save(existingCompany);
    }

    @Override
    public void deleteCompanyById(Long id) {
        Company existingCompany = companyRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));
        companyRepo.delete(existingCompany);

    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {

//        System.out.println("Review message ::"+reviewMessage.getDescription());
        Company company = companyRepo.findById(reviewMessage.getCompanyId())
                .orElseThrow(()->new NotFoundException("Company not found "+ reviewMessage.getCompanyId()));
        double avgRating = reviewClient.getAverageRatingForCompany(company.getId());
        company.setRating(avgRating);
        companyRepo.save(company);
    }
}
