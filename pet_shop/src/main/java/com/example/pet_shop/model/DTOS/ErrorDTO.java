package com.example.pet_shop.model.DTOS;

import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record ErrorDTO ( Object msg , int status , LocalDateTime time ){}
