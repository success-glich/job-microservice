package com.myjob.review.review.exception;

public class DuplicateResourceException extends  RuntimeException{
    public DuplicateResourceException(String message){
        super(message);
    }
}
