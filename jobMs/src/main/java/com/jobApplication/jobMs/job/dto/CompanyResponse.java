package com.jobApplication.jobMs.job.dto;

public class CompanyResponse {
    private boolean success;
    private String message;
    private CompanyDTO data;

    public CompanyResponse() {}

    public CompanyResponse(boolean success, String message, CompanyDTO data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CompanyDTO getData() {
        return data;
    }

    public void setData(CompanyDTO data) {
        this.data = data;
    }
}