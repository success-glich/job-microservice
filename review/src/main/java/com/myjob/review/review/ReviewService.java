package com.myjob.review.review;

import com.myjob.review.review.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviewsByCompanyId(Long companyId);
    Review addReview(Long companyId, ReviewDto review);
    Review getByIdAndCompanyId(Long reviewId,Long companyId);
    void deleteReview(Long companyId, Long reviewId);

}
