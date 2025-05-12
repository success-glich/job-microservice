package com.myjob.review.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepo extends JpaRepository<Review,Long> {
    List<Review> findByCompanyId(Long companyId);
    Optional<Review> findByIdAndCompanyId(Long id, Long companyId);
}
