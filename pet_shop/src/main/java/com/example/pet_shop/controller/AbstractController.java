package com.example.pet_shop.controller;

import com.example.pet_shop.model.DTOS.ErrorDTO;
import com.example.pet_shop.exceptions.BadRequestException;
import com.example.pet_shop.exceptions.NotFoundException;
import com.example.pet_shop.exceptions.UnauthorizedException;
import com.example.pet_shop.model.DTOS.orderDTO.CartDTO;
import com.example.pet_shop.model.entities.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequest(Exception e){
        return generateErrorDTO(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleUnauthorized(Exception e){
        return generateErrorDTO(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(Exception e){
        return generateErrorDTO(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleRest(Exception e){
        return generateErrorDTO(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDTO generateErrorDTO(Object o, HttpStatus s){
        return ErrorDTO.builder()
                .msg(o)
                .time(LocalDateTime.now().toString())
                .status(s.value())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDTO handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return generateErrorDTO(errors, HttpStatus.BAD_REQUEST);
    }
    protected CartDTO getCart(HttpSession ses){
        CartDTO cart;

        if (ses.getAttribute("cart") == null) {
            cart = new CartDTO();
            ses.setAttribute("cart", cart);
        }else {
            cart = (CartDTO) ses.getAttribute("cart");
        }

        return cart;
    }
    protected void checkAuthorization(LoginManager log) {
        if (!log.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
        if (!log.isAdmin()) {
            throw new UnauthorizedException("You are not admin");
        }
    }
    protected void checkLogin(LoginManager log){
        if (!log.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        }
    }

    protected int getLoggedUserId(LoginManager log) {
        if (!log.isLogged()) {
            throw new BadRequestException("You have to be logged in!");
        } else {
            return log.id();
        }
    }

}
