package com.myjob.companyms.company;

import com.myjob.companyms.company.Impl.CompanyServiceImpl;
import com.myjob.companyms.company.dto.CompanyRequest;
import com.myjob.companyms.company.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class CompanyServiceImplTests {

    @InjectMocks
     private CompanyServiceImpl companyService;

     @Mock
     private CompanyRepo companyRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCompanies() {
        List<Company> companies = List.of(new Company(1L, "Test Company", "Description"));
        when(companyRepo.findAll()).thenReturn(companies);

        List<Company> result = companyService.getAllCompanies();
        assertEquals(1, result.size());
        assertEquals("Test Company", result.getFirst().getName());
    }

    @Test
    void testGetCompanyById() {

        //* mocking the companyRepo to return a company when findById is called
        Company company = new Company(1L, "Test Company", "Description");
        when(companyRepo.findById(1L)).thenReturn(Optional.of(company));

        Company result = companyService.getCompanyById(1L);

        assertEquals("Test Company", result.getName());
    }

    @Test
    void testGetCompanyByIdNotFound() {
        when(companyRepo.findById(1L)).thenReturn(Optional.empty());

        // Calling the method and expecting an exception
        Exception exception = assertThrows(RuntimeException.class, () -> {
            companyService.getCompanyById(1L);
        });


        assertEquals("Company not found with id: 1", exception.getMessage());
    }


    @Test
    void testCreateCompany() {
        // Given
        CompanyRequest companyRequest = new CompanyRequest();
        companyRequest.setName("Success Technologies");
        companyRequest.setDescription("This is new success technologies");

        Company expectedCompany = new Company(1L, "Success Technologies", "This is new success technologies");

        when(companyRepo.save(any(Company.class))).thenReturn(expectedCompany);

        Company result = companyService.createCompany(companyRequest);


        assertNotNull(result);
        assertEquals(companyRequest.getName(), result.getName());
        assertEquals(companyRequest.getDescription(), result.getDescription());

        verify(companyRepo).save(any(Company.class));
    }

    @Test
    void testUpdateCompany_Success(){
        Company existing = new Company(1L,"Test Company", "Description");

        CompanyRequest request = new CompanyRequest();
        request.setName("Updated Company");
        request.setDescription("Updated Description");

        when(companyRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(companyRepo.save(any(Company.class))).thenReturn(existing);

        Company result = companyService.updateCompany(1L, request);
        assertEquals("Updated Company", result.getName());
    }

    @Test
    void  testDeleteCompany_Success(){
        Company company = new Company(1L, "Test Company", "Description");
        when(companyRepo.findById(1L)).thenReturn(Optional.of(company));

        doNothing().when(companyRepo).delete(company);

        assertDoesNotThrow(() -> companyService.deleteCompanyById(1L));
        verify(companyRepo, times(1)).delete(company);
    }
    @Test
    void testDeleteCompany_NotFound() {
        when(companyRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> companyService.deleteCompanyById(1L));
    }
}
