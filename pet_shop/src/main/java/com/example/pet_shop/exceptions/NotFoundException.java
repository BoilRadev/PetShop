package com.example.pet_shop.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg){
        super(msg);
    }
}
