
package com.myjob.companyms.company;

import com.myjob.companyms.company.dto.CompanyRequest;
import com.myjob.companyms.company.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Company>>> getAllCompanies() {
        List<Company> allCompanies = companyService.getAllCompanies();
        return ResponseEntity.ok(new ApiResponse<>(true,"All companies fetched successfully.",allCompanies));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Company>> createCompany(@Valid @RequestBody CompanyRequest companyRequest, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            return ResponseEntity.badRequest().body(new ApiResponse<>(
                    false,
                    "all fields are required",
                    null
            ));

        }

        Company company = companyService.createCompany(companyRequest);

        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Company created successfully",
                company
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Company>> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);


        return ResponseEntity.ok(new ApiResponse<>(true, "Company fetched successfully.", company));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Company>> updateCompanyById(@Valid @RequestBody CompanyRequest companyRequest,@PathVariable Long id, BindingResult bindingResult ){

        if(bindingResult.hasErrors()) return  ResponseEntity.badRequest().body(new ApiResponse<>(true,"All fields are required.",null));

        Company updatedCompany = companyService.updateCompany(id,companyRequest);

        return ResponseEntity.ok(new ApiResponse<>(true,"Company updated successfully.",updatedCompany));

    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<String> deleteCompanyById(@PathVariable Long id){
        companyService.deleteCompanyById(id);
        return ResponseEntity.noContent().build();
    }



}


/*


// demo controller
 public ResponseEntity<ApiResponse<List<Company>>> getAllCompanies() {
        try {
            List<Company> companies = companyRepo.findAll();

            if (companies.isEmpty()) {
                ApiResponse<List<Company>> response = new ApiResponse<>(
                        HttpStatus.NO_CONTENT.value(),
                        "No companies found",
                        null
                );
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
            }

            ApiResponse<List<Company>> response = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Companies fetched successfully",
                    companies
            );
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            ApiResponse<List<Company>> response = new ApiResponse<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An error occurred while fetching companies",
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
 */