package com.myjob.review.review.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewDto {
    @NotNull
    @NotBlank(message = "title is required")
    private String title;

    @NotNull
    @NotBlank(message = "description is required")
    private String description;

    @DecimalMax("5.0")
    @DecimalMin("0.0")
    private Double rating =0.0;

}
