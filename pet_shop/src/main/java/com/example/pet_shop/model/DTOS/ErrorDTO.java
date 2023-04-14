package com.example.pet_shop.model.DTOS;

import lombok.*;

import java.time.LocalDateTime;


@Builder
public record ErrorDTO ( String msg , int status , LocalDateTime time ){}
