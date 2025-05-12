package com.myjob.review.review.Impl;

import com.myjob.review.review.exception.ResourceNotFoundException;
import com.myjob.review.review.Review;
import com.myjob.review.review.ReviewRepo;
import com.myjob.review.review.ReviewService;
import com.myjob.review.review.dto.ReviewDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;

    @Override
    public List<Review> getAllReviewsByCompanyId(Long companyId) {
        return reviewRepo.findByCompanyId(companyId);
    }

    @Override
    public Review addReview(Long companyId, ReviewDto review) {

        Review newReview = new Review();
        newReview.setCompanyId(companyId);
        newReview.setTitle(review.getTitle());
        newReview.setRating(review.getRating());
        newReview.setDescription(review.getDescription());
        return reviewRepo.save(newReview);
    }

    @Override
    public Review getByIdAndCompanyId(Long companyId, Long reviewId) {
        return reviewRepo.findByIdAndCompanyId(reviewId, companyId).orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId + " for company id: " + companyId));
    }

    @Override
    public void deleteReview(Long companyId, Long reviewId) {
        Review existingReview = reviewRepo.findByIdAndCompanyId(reviewId, companyId).orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + reviewId + " for company id: " + companyId));
        reviewRepo.delete(existingReview);
    }

}
