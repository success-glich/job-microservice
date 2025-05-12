package com.myjob.companyms.company.utils;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse <T>{
    private boolean success;
    private String message;
    private T data;
}
