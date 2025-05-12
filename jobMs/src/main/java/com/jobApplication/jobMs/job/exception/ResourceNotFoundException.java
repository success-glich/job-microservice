package com.jobApplication.jobMs.job.exception;

public class ResourceNotFoundException  extends  RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}
