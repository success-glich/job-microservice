package com.myjob.companyms.company;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CompanyServiceTest {
    // Test cases for CompanyService methods
    // 1. getAllCompanies
    // 2. createCompany
    // 3. getCompanyById
    // 4. updateCompany
    // 5. deleteCompanyById

    // Mock the dependencies and write test cases for each method



    @Autowired
    private CompanyRepo companyRepo;

    @Disabled
    @Test
    public void testSomething(){
        assertEquals(6, 3+3);
    }


    @Disabled
    @Test
     void testFindById(){
        // Mock the repository and test the findById method
        // Use Mockito or any other mocking framework
//        assertNotNull(companyRepo.findById(1L));

        Company company = companyRepo.findById(3L).orElse(null);
        assertNotNull(company);
    }


    @ParameterizedTest
    @CsvSource({
            "1, 2, 3",
            "4, 5, 9",
            "10, 20, 30",
            "100, 200, 300",
            "3, 6, 9"
    })
    void test(int a, int b,int expected){

        assertEquals(expected,a+b);
    }

}
