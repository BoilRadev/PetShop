package com.example.pet_shop.model.DTOS;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO{

    private Object msg;
    private int status;
    private String time;
}
