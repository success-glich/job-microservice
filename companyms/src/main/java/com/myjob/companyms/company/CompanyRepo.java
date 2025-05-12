package com.myjob.companyms.company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company,Long> {
    // JpaRepository provides CRUD operations and more
    // No need to define any methods here unless you want custom queries
    // For example, you can define a method to find companies by name or other attributes
}