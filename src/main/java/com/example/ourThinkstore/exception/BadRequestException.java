package com.example.ourThinkstore.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String str){
         super(str);
    }
}
