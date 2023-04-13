package com.example.pet_shop.model.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String msg){
        super(msg);
    }
}
