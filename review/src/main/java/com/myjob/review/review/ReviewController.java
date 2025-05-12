package com.myjob.review.review;

import com.myjob.review.review.messaging.ReviewMessageProducer;
import com.myjob.review.review.dto.ReviewDto;
import com.myjob.review.review.utils.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService  reviewService;

    @Autowired
    private ReviewMessageProducer reviewMsgProducer;


    @GetMapping
    public ResponseEntity<ApiResponse<List<Review>>> getAllReviews(@RequestParam Long companyId) {
        List<Review> allReviews = reviewService.getAllReviewsByCompanyId(companyId);
        return ResponseEntity.ok(new ApiResponse<>(true, "All reviews fetched successfully.", allReviews));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Review>> addReview(@RequestParam Long companyId, @Valid @RequestBody ReviewDto reviewDto) {

        Review review = reviewService.addReview(companyId, reviewDto);

        reviewMsgProducer.sendMessage(review);
        return ResponseEntity.ok(new ApiResponse<>(true, "Review added successfully.", review));
    }

    @GetMapping("/{reviewId}")
    public  ResponseEntity<ApiResponse<Review>> getReviewById(@RequestParam Long companyId, @PathVariable Long reviewId) {
        Review review = reviewService.getByIdAndCompanyId(companyId, reviewId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Review fetched successfully.", review));
    }

    @DeleteMapping("/{reviewId}")
    public  ResponseEntity<ApiResponse<Object>> deleteReview(@RequestParam Long companyId, @PathVariable Long reviewId) {
        reviewService.deleteReview(companyId, reviewId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Review deleted successfully.", null));
    }

    @GetMapping("/averageRating")
    public  Double getAverageReview(@RequestParam Long companyId){
        List<Review> reviewList = reviewService.getAllReviewsByCompanyId(companyId);
        return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }


}
