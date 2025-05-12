package com.myjob.review.review.dto;

import com.myjob.review.review.Review;
import lombok.Data;


@Data
public class ReviewWithCompanyDto {
    Review review;
    CompanyInfo companyInfo;
}
