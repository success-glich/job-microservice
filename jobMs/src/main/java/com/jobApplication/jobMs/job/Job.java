package com.jobApplication.jobMs.job;

import com.jobApplication.jobMs.job.utils.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "jobs")
public class Job extends Auditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "Description should not be not null")
    private String description;

    @NotNull
    @Column(name = "min_salary")
    private Double minSalary;

    @NotNull
    @Column(name = "max_salary")
    private Double maxSalary;


    private Long companyId;




}