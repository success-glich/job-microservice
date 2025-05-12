package com.myjob.review.review;

import com.myjob.review.review.utils.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name="reviews")
public class Review extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title",nullable = false)
    private String title;

    @Column(name="description",nullable = false,columnDefinition = "TEXT")
    private  String description;

    @Column(name="rating",nullable = false)
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private  Double rating =0.0;

    private Long companyId;



}
